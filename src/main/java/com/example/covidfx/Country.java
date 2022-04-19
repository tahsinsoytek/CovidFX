package com.example.covidfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Country implements Comparable<Country>
{

    private String name;
    private TreeMap<LocalDate,Integer> localDateIntegerHashMap;
    public Country(String name)
    {
        localDateIntegerHashMap=new TreeMap<>();
        this.name=name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;
        Country country = (Country) o;
        return name.equals(country.name);
    }
    public void addLocalDateIntegerHashMap(LocalDate localDate,Integer x)
    {
        localDateIntegerHashMap.put(localDate,x);
    }

    public ObservableList<String> getLocalDates()
    {
        ObservableList<String> localDates=FXCollections.observableArrayList();
        for(LocalDate temp:localDateIntegerHashMap.keySet())
        {
            localDates.add(temp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
        return localDates;
    }

    public String getName()
    {
        return name;
    }

    public ObservableList<Integer> getDeathNumbers()
    {
        ObservableList<Integer> deathNumbers= FXCollections.observableArrayList();
        for(Integer temp:localDateIntegerHashMap.values())
        {
            deathNumbers.add(temp);
        }
        return deathNumbers;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name);
    }

    @Override
    public String toString()
    {
        return this.name;
    }

    @Override
    public int compareTo(Country o)
    {
        if(this.name.compareTo(o.name)>0)
        {
            return 1;
        }
        else if(this.name.compareTo(o.name)<0)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
}
