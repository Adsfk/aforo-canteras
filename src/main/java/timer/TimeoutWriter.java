package timer;

import model.SeaHeightScrapper;
import data_scraper.mediciones.TideMeasures;

import java.util.Timer;

public class TimeoutWriter {

    public void timeout(){
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask(new TideMeasures(), SeaHeightScrapper.getInstance()),0,60*1000);
    }
}
