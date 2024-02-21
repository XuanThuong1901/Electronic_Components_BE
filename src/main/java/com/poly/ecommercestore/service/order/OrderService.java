package com.poly.ecommercestore.service.order;

import com.poly.ecommercestore.entity.*;
import com.poly.ecommercestore.entity.embeddable.DetailOrderId;
import com.poly.ecommercestore.repository.*;
import com.poly.ecommercestore.model.request.DetailOrderRequest;
import com.poly.ecommercestore.model.request.OrderRequest;
import com.poly.ecommercestore.util.extractToken.IExtractToken;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService implements IOrderService{

    @Autowired
    private IExtractToken iExtractToken;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private DetailOrderRepository detailOrderRepository;

    @Autowired
    private  AccountRepository accountRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private ShippingUnitRepository shippingUnitRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CartRepository cartRepository;

    private final int UNCONFIRMED = 3;
    private final int CONFIRMED = 4;
    private final int DELIVERY = 5;
    private final int DELIVERED = 6;
    private final int CANCELED = 7;

    private final int ONE_HUNDRED = 100;

    private final String HANOI = "Hà Nội";
    private final String DANANG = "Đà Nẵng";
    private final String TPHCM = "Hồ Chí Minh";
    private final String EXPORT = "export";

    @Override
    public Orders getOneOrder(String tokenHeader, int iDOrder) {
        return orderRepository.findById(iDOrder).orElse(null);
    }

    @Override
    public List<Orders> getOrderByCustomer(String tokenHeader) {
        Customers customer = iExtractToken.extractCustomer(tokenHeader);
        return orderRepository.findByCustomer(customer);
    }

    @Override
    public List<Orders> getAllOrder(String tokenHeader) {
        Employers employer= iExtractToken.extractEmployee(tokenHeader);
        if(employer == null)
            return null;
        return orderRepository.findAll();
    }

    @Transactional
    @Override
    public int addOrder(String tokenHeader, OrderRequest request) {

        if(request.getDetailOrders().size() == 0)
            return 0;
        Customers customer = iExtractToken.extractCustomer(tokenHeader);
        ShippingUnits shippingUnit = shippingUnitRepository.getReferenceById(request.getShippingUnit());
        Payments payment = paymentRepository.getReferenceById(request.getPayment());
        Status status = statusRepository.getReferenceById(UNCONFIRMED);

        if(customer == null || shippingUnit == null || payment == null || status == null)
            return 0;
        Orders newOrder = new Orders();
        Date dateOrder = new Date();

        newOrder.setAddress(request.getAddress());
        newOrder.setTelephone(request.getTelephone());
        newOrder.setNote(request.getNote());
        newOrder.setDateOrder(dateOrder);
        newOrder.setDiscountAmount(request.getDiscountAmount());
        newOrder.setCustomer(customer);
        newOrder.setShippingUnit(shippingUnit);
        newOrder.setPayment(payment);
        newOrder.setStatus(status);

        if(checkAddressShipping(request.getAddress())){
            newOrder.setShippingFee(new BigDecimal(20000));
        }
        else {
            newOrder.setShippingFee(new BigDecimal(30000));
        }
        newOrder = orderRepository.save(newOrder);

        List<DetailOrders> newDetailOrders = new ArrayList<>();
        BigDecimal amount = newOrder.getShippingFee();
        BigDecimal taxAmount = new BigDecimal(0);
        for(DetailOrderRequest detailOrder : request.getDetailOrders()){
            Products product = productRepository.getReferenceById(detailOrder.getProduct());

            if(product == null || product.getQuantity() < detailOrder.getQuantity())
            {
                orderRepository.delete(newOrder);
                return 2;
            }

            Carts cart = cartRepository.findByCustomerAndProduct(customer.getIDCustomer(), product.getIDProduct());
            if(cart == null)
            {
                orderRepository.delete(newOrder);
                return 0;
            }
            cartRepository.delete(cart);
            BigDecimal priceProduct = new BigDecimal(0);
            for (PriceLists priceList : product.getPriceLists()){
                if(priceList.getStatus() && priceList.getType().equalsIgnoreCase(EXPORT)){
                    priceProduct = priceProduct.add(priceList.getPrice());
                }
            }
//            BigDecimal countTax = countTax(priceProduct, product.getTax().getTaxPercentage());
            BigDecimal lineAmount = priceProduct.multiply(BigDecimal.valueOf(detailOrder.getQuantity()));
            taxAmount = taxAmount.add(lineAmount.multiply(BigDecimal.valueOf(((double) product.getTax().getTaxPercentage()/100.0))));

            DetailOrderId detailOrderId = new DetailOrderId(newOrder.getIDOrder(), product.getIDProduct());
            DetailOrders detailOrderTemp = new DetailOrders(detailOrderId, detailOrder.getQuantity(), priceProduct, lineAmount, newOrder, product, product.getTax(), false);


            newDetailOrders.add(detailOrderTemp);
            amount = amount.add(lineAmount);
            product.setQuantity(product.getQuantity() - detailOrder.getQuantity());
            productRepository.save(product);
            detailOrderRepository.save(detailOrderTemp);
        }

        amount = amount.add(taxAmount);
        newOrder.setAmount(amount);
        newOrder.setTaxAmount(taxAmount);
        newOrder.setDetailOrders(newDetailOrders);

        orderRepository.save(newOrder);
        return 1;
    }

    @Override
    public Boolean statusConfirmedOrder(String tokenHeader, int iDOrder) {

        Employers employer = iExtractToken.extractEmployee(tokenHeader);
        Orders order = orderRepository.getReferenceById(iDOrder);
        if(employer == null || order == null)
            return false;
        if(order.getEmployer() == null)
            order.setEmployer(employer);
        if(order.getStatus().getIDStatus() == UNCONFIRMED){
            order.setStatus(statusRepository.getReferenceById(CONFIRMED));
            orderRepository.save(order);

            return true;
        }

        return false;
    }

    @Override
    public Boolean statusDeliveryOrder(String tokenHeader, int iDOrder) {
        Employers employer = iExtractToken.extractEmployee(tokenHeader);
        Orders order = orderRepository.findById(iDOrder).orElse(null);
        if(employer == null || order == null)
            return false;

        if(order.getStatus().getIDStatus() == CONFIRMED){
            order.setStatus(statusRepository.findById(DELIVERY).get());
            orderRepository.save(order);

            return true;
        }

        return false;
    }

    @Override
    public Boolean statusDeliveredOrder(String tokenHeader, int iDOrder) {
        Employers employer = iExtractToken.extractEmployee(tokenHeader);
        Orders order = orderRepository.findById(iDOrder).orElse(null);
        if(employer == null || order == null)
            return false;

        if(order.getStatus().getIDStatus() == DELIVERY){
            order.setStatus(statusRepository.findById(DELIVERED).get());
            orderRepository.save(order);

            return true;
        }

        return false;
    }

    @Override
    public Boolean statusCanceledOrder(String tokenHeader, int iDOrder) {
        Employers employer = iExtractToken.extractEmployee(tokenHeader);
        Orders order = orderRepository.getReferenceById(iDOrder);
        if(employer == null || order == null)
            return false;

        if(order.getStatus().getIDStatus() == UNCONFIRMED ||order.getStatus().getIDStatus() == CONFIRMED){

            Status status = statusRepository.findById(CANCELED).get();
            order.setStatus(status);
            for (DetailOrders detailOrder : order.getDetailOrders()){

                Products product = detailOrder.getProduct();
                product.setQuantity(product.getQuantity() + detailOrder.getQuantity());

                productRepository.save(product);
            }
            orderRepository.save(order);
            return true;
        }

        return false;
    }

    private Boolean checkAddressShipping(String address){
        if(address.contains(HANOI))
            return true;
        if(address.contains(DANANG))
            return true;
        if(address.contains(TPHCM))
            return true;

        return false;
    }

}
