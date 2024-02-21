package com.poly.ecommercestore.service.report;

import com.poly.ecommercestore.model.request.ReportRequest;
import com.poly.ecommercestore.entity.ImportStocks;
import com.poly.ecommercestore.entity.Orders;
import com.poly.ecommercestore.repository.ImportStockRepository;
import com.poly.ecommercestore.repository.OrderRepository;
import com.poly.ecommercestore.model.response.report.ReportSaleProductResponse;
import com.poly.ecommercestore.model.response.report.ReportSaleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements IReportService{

    private final ImportStockRepository importStockRepository;
    private final OrderRepository orderRepository;

    @Override
    public ReportSaleResponse reportSale(ReportRequest request) {

//        List<BigDecimal> saleOrder = saleOrder(request);
//        List<BigDecimal> saleImport = saleImport(request);
        ReportSaleResponse response = ReportSaleResponse.builder()
                .saleOrder(saleOrder(request))
                .saleImport(saleImport(request))
                .dateList(listDay(request))
                .message("REPORT_SALE_SUCCESS")
                .build();
        return response;
    }

    @Override
    public ReportSaleProductResponse reportSaleProduct(ReportRequest request) {
        return null;
    }

    private List<BigDecimal> saleImport(ReportRequest request){

        Calendar calendarA = Calendar.getInstance();
        calendarA.setTime(request.getStartDay());

        Calendar calendarB = Calendar.getInstance();
        calendarB.setTime(request.getEndDay());

        int weeksBetween = 0;
        int monthsBetween = 0;
        int yearsBetween = 0;

        long milliseconds = Math.abs(calendarA.getTimeInMillis() - calendarB.getTimeInMillis());
        long weeks = (long) Math.ceil(milliseconds / (1000.0 * 60 * 60 * 24 * 7));

        if (weeks < 8) {
            weeksBetween += weeks;
        } else {
            long months = (long) Math.ceil(weeks / 4.0); // Giả sử 1 tháng có 4 tuần
            if (months < 13) {
                monthsBetween += months;
            } else {
                yearsBetween += months / 12;
            }
        }

        List<ImportStocks> importStocksList = importStockRepository.findByStartEndDay(request.getStartDay(), request.getEndDay());

        if(importStocksList == null)
            return null;

        List<BigDecimal> saleImportList = new ArrayList<>();

        if(weeksBetween > 0){
            saleImportList = countSaleImport(importStocksList, weeksBetween, calendarA, "WEEK");
        }
        else if(monthsBetween > 0){
            saleImportList = countSaleImport(importStocksList, monthsBetween, calendarA, "MONTH");
        }
        else if (yearsBetween > 0){
            saleImportList = countSaleImport(importStocksList, yearsBetween, calendarA, "YEAR");
        }else {
            Iterator<ImportStocks> iterator = importStocksList.iterator();
            BigDecimal saleWeekTemp = BigDecimal.valueOf(0);
            while (iterator.hasNext()){
                ImportStocks item = iterator.next();
                saleWeekTemp = saleWeekTemp.add(item.getAmount());
                iterator.remove();
            }
            saleImportList.add(saleWeekTemp);
        }

        return saleImportList;
    }

    private List<Date> listDay( ReportRequest request){

        Calendar calendarA = Calendar.getInstance();
        calendarA.setTime(request.getStartDay());

        Calendar calendarB = Calendar.getInstance();
        calendarB.setTime(request.getEndDay());

//        int weeksBetween = 0;
//        int monthsBetween = 0;
//        int yearsBetween = 0;
        int dateBetween = 0;
        String key = "WEEK";
        long milliseconds = Math.abs(calendarA.getTimeInMillis() - calendarB.getTimeInMillis());
        long weeks = (long) Math.ceil(milliseconds / (1000.0 * 60 * 60 * 24 * 7));

        if (weeks < 8) {
            dateBetween += weeks;
            key = "WEEK";
        } else {
            long months = (long) Math.ceil(weeks / 4.0); // Giả sử 1 tháng có 4 tuần
            if (months < 13) {
                dateBetween += months;
                key = "MONTH";
            } else {
                dateBetween += months / 12;
                key = "YEAR";
            }
        }

        List<Date> listDay = new ArrayList<>();
        for(int i = 0; i< dateBetween; i++){
            Calendar calendarCountDay = Calendar.getInstance();
            calendarCountDay.setTime(request.getStartDay());

            int day = (int) (i+1) * 7;

            if(key.equals("MONTH"))
                day = day * 4;
            if(key.equals("YEAR"))
                day = day * ( 4 * 12);

            calendarCountDay.add(Calendar.DAY_OF_MONTH, day);
            Date dateFromCalendar = calendarCountDay.getTime();

            if(i == dateBetween - 1 && dateFromCalendar.after(request.getEndDay())){
                listDay.add((request.getEndDay()));
                break;
            }
            listDay.add(dateFromCalendar);
        }

        return listDay;
    }

    private List<BigDecimal> countSaleImport(List<ImportStocks> importStocks, int dateBetween, Calendar startDay, String type){

        List<BigDecimal> saleImportList = new ArrayList<>();
        Iterator<ImportStocks> iterator = importStocks.iterator();

        for (int i = 0; i<dateBetween; i++){

            BigDecimal saleWeek = BigDecimal.valueOf(0);
            while (iterator.hasNext()){
                ImportStocks item = iterator.next();
                Calendar calendarA = Calendar.getInstance();
                calendarA.setTime(item.getDateAdded());

                long milliseconds = Math.abs(startDay.getTimeInMillis() - calendarA.getTimeInMillis());
                float weeks = (float) milliseconds / (1000 * 60 * 60 * 24 * 7);

                if(type.equals("MONTH"))
                    weeks = (float) weeks / 4;
                if(type.equals("YEAR"))
                    weeks = (float) weeks / ( 4 * 12);

                if(weeks > i + 1)
                    break;
                saleWeek = saleWeek.add(item.getAmount());
                iterator.remove();
            }
            saleImportList.add(saleWeek);
            if(i == dateBetween - 1 && iterator.hasNext()){
                BigDecimal saleWeekTemp = BigDecimal.valueOf(0);
                while (iterator.hasNext()){
                    ImportStocks item = iterator.next();
                    saleWeekTemp = saleWeekTemp.add(item.getAmount());
                    iterator.remove();
                }
                saleImportList.add(saleWeekTemp);
            }
        }

        return saleImportList;
    }


    private List<BigDecimal> saleOrder(ReportRequest request){

        Calendar calendarA = Calendar.getInstance();
        calendarA.setTime(request.getStartDay());

        Calendar calendarB = Calendar.getInstance();
        calendarB.setTime(request.getEndDay());

        int weeksBetween = 0;
        int monthsBetween = 0;
        int yearsBetween = 0;

        long milliseconds = Math.abs(calendarA.getTimeInMillis() - calendarB.getTimeInMillis());
        long weeks = (long) Math.ceil(milliseconds / (1000.0 * 60 * 60 * 24 * 7));

        if (weeks < 8) {
            weeksBetween += weeks;
        } else {
            long months = (long) Math.ceil(weeks / 4.0); // Giả sử 1 tháng có 4 tuần
            if (months < 13) {
                monthsBetween += months;
            } else {
                yearsBetween += months / 12;
            }
        }

        List<Orders> ordersList = orderRepository.findByStartEndDay(request.getStartDay(), request.getEndDay());

        if(ordersList == null)
            return null;

        List<BigDecimal> saleImportList = new ArrayList<>();

        if(weeksBetween != 0){
            saleImportList = countSaleOrder(ordersList, weeksBetween, calendarA, "WEEK");
        }
        else if(monthsBetween != 0){
            saleImportList = countSaleOrder(ordersList, monthsBetween, calendarA, "MONTH");
        }
        else if (yearsBetween != 0){
            saleImportList = countSaleOrder(ordersList, yearsBetween, calendarA, "YEAR");
        }else {
            Iterator<Orders> iterator = ordersList.iterator();
            BigDecimal saleWeekTemp = BigDecimal.valueOf(0);
            while (iterator.hasNext()){
                Orders item = iterator.next();
                saleWeekTemp = saleWeekTemp.add(item.getAmount());
                iterator.remove();
            }
            saleImportList.add(saleWeekTemp);
        }

        return saleImportList;
    }

    private List<BigDecimal> countSaleOrder(List<Orders> ordersList, int dateBetween, Calendar startDay, String type){

        List<BigDecimal> saleOrderList = new ArrayList<>();
        Iterator<Orders> iterator = ordersList.iterator();

        for (int i = 0; i<dateBetween; i++){
            BigDecimal saleWeek = BigDecimal.valueOf(0);
            while (iterator.hasNext()){
                Orders item = iterator.next();
                Calendar calendarA = Calendar.getInstance();
                calendarA.setTime(item.getDateOrder());

                long milliseconds = Math.abs(startDay.getTimeInMillis() - calendarA.getTimeInMillis());
                float weeks = (float) milliseconds / (1000 * 60 * 60 * 24 * 7);

                if(type.equals("MONTH"))
                    weeks = (float) weeks / 4;
                if(type.equals("YEAR"))
                    weeks = (float) weeks / ( 4 * 12);

                if(weeks > i + 1)
                    break;
                saleWeek = saleWeek.add(item.getAmount());
                iterator.remove();
            }
            saleOrderList.add(saleWeek);
            if(i == dateBetween - 1 && iterator.hasNext()){
                BigDecimal saleWeekTemp = BigDecimal.valueOf(0);
                while (iterator.hasNext()){
                    Orders item = iterator.next();
                    saleWeekTemp = saleWeekTemp.add(item.getAmount());
                    iterator.remove();
                }
                saleOrderList.add(saleWeekTemp);
            }
        }

        return saleOrderList;
    }
}