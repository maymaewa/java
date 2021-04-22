package com.oop;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Comparator;


public class HitParade {
    private ArrayList<Group> Hit = new ArrayList<Group>();

    public HitParade() {
    }

    public ArrayList<Group> getTop3(){
        ArrayList<Group> top3 = new ArrayList<Group>();
        for (int i = 0; i < 3; i++){
            top3.add(Hit.get(i));
        }
        return top3;

    }


    public void showHit() {

        for(Group group : Hit){

            group.getInfo();
        }
    }
    public void sortHit(){
        Hit.sort(Comparator.comparing(Group::getRating));
    }

    public void addGroupInTop(Group group){
        Hit.add(group);
        sortHit();
    }

    public void setTop(){

    }

    public void deleteGroup(Group group){
        Hit.remove(group);
    }

    public int getPosOfGroup(Group group){
        int pos = Hit.indexOf(group);
        return pos;
    }


}
