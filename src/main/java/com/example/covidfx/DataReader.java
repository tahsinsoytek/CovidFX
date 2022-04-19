package com.example.covidfx;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;


public class DataReader
{
    static DataReader instance=new DataReader();
    String line;
    URL url;
    private TreeSet<Country> countries;

    public TreeSet<Country> getCountries()
    {
        return countries;
    }

    private DataReader()
    {
        url=Controller.getUrl();
        countries=new TreeSet<>();
    }

    public static DataReader getInstance()
    {
        return instance;
    }

    public void dataPulling()
    {
        try
        {
            HttpURLConnection hr=(HttpURLConnection) url.openConnection();
            try (InputStream inputStream = hr.getInputStream())
            {
                String name;
                LocalDate localDate;
                Integer x;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                while((line = bufferedReader.readLine()) != null)
                {
                    if(line.contains("dateRep"))
                    {
                        line=line.replaceAll("<dateRep>","");
                        line=line.replaceAll("</dateRep>","");
                        line=line.trim();
                        localDate=LocalDate.parse(line,DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        for(int i=0;i<5;i++)
                        {
                            line=bufferedReader.readLine();
                        }
                        line=line.replaceAll("<deaths>","");
                        line=line.replaceAll("</deaths>","");
                        x=Integer.parseInt(line.trim());
                        line=bufferedReader.readLine();
                        line=line.replaceAll("<countriesAndTerritories>","");
                        line=line.replaceAll("</countriesAndTerritories>","");
                        name=line.trim();
                        addDeathsAndDate(localDate,x,name);
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    private Country addCountry(String element)
    {
        Country country=null;
        if(!(countries.contains(new Country(element))))
        {
            country=new Country(element);
            countries.add(country);
            return country;
        }
        else
        {
            country=countries.ceiling(new Country(element));
            return country;
        }
    }
    private void addDeathsAndDate(LocalDate localDate,Integer x,String name)
    {
        Country country;
        country=addCountry(name);
        country.addLocalDateIntegerHashMap(localDate,x);
    }
}
