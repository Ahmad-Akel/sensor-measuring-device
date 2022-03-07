package com.example.akel_semprace.DataLayer;
import java.util.concurrent.ThreadLocalRandom;

import java.util.*;

public   class Generator {


    public static List<RowInput> Generate(int count)
    {
        if(count <=0)
            return null;
        var GeneratedRows=  new ArrayList<RowInput>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        for (int i = 1; i<=count;i++)
        {
            var row = new RowInput();
            row.setID(ThreadLocalRandom.current().nextInt(1, i*2 + 1));
            row.setM(ThreadLocalRandom.current().nextDouble(0.00,5.0));
            row.setSensorID(ThreadLocalRandom.current().nextInt(1, i*2 + 1));
            cal.add(Calendar.HOUR_OF_DAY,i);
            row.setDate(cal.getTime());
            GeneratedRows.add(row);
        }

        return  GeneratedRows;
    }
}
