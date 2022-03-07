package com.example.akel_semprace.DataLayer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class CSVReader {

    public List<RowInput> readFile(String path) throws ParseException {
        String line = "";
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int count = 0;
        ArrayList<RowInput> rows = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                if (count == 0) {
                    count++;
                    continue;
                }
                String[] values = line.split(";");
                RowInput rowIn = new RowInput();
                rowIn.setID(Integer.parseInt(values[0].trim()));
                rowIn.setSensorID(Integer.parseInt(values[1].trim()));
                rowIn.setM(Double.parseDouble(values[3]));
                rowIn.setDate(df.parse(values[2]));
                rows.add(rowIn);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSVReader.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(CSVReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(CSVReader.class.getName()).log(Level.SEVERE, null, ex);
        }
      return  rows;
    }
}
