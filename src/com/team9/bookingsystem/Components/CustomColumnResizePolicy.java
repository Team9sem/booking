package com.team9.bookingsystem.Components;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

/**
 * Created by pontuspohl on 18/11/15.
 */
public class CustomColumnResizePolicy implements Callback<TableView.ResizeFeatures, Boolean> {

    @Override
    public Boolean call(TableView.ResizeFeatures param){

        TableView tableView = param.getTable();
        Double tableWidth = tableView.widthProperty().getValue();
        if(tableWidth == null || tableWidth <= 0.0 ){
            return false;
        }
        if(tableView.getColumns().size() != 0){


        int colsToSize = 0;
        Double fixedWidth = 0.0;
        ArrayList<TableColumn> columnsInTable = new ArrayList<>(tableView.getColumns());

        for (TableColumn col : columnsInTable)
        {
            if (col.isResizable() && col.isVisible())
            {
                colsToSize++;
            }
            else if (col.isVisible())
            {
                fixedWidth += col.getWidth();
            }
        }
            System.out.println(fixedWidth);
        if (colsToSize == 0){
            return TableView.UNCONSTRAINED_RESIZE_POLICY.call(param);
        }
        TableColumn last = null;
        for (TableColumn col : columnsInTable){
            if(col.isResizable() && col.isVisible()){
                double newWidth = (tableWidth - fixedWidth) / colsToSize;
                col.setPrefWidth(newWidth);
                last = col;
            }
        }
        if(last != null){
            Double borderWidth = ( (tableView.getBoundsInLocal().getWidth()-tableView.widthProperty().doubleValue()) ) ;

//            Double borderWidth = tableView.widthProperty().doubleValue() - 2.0;
            last.setPrefWidth(last.getPrefWidth()-borderWidth);
        }

        return true;
        }
        else{
            return TableView.UNCONSTRAINED_RESIZE_POLICY.call(param);
        }


    }


}
