package com.ezesky.likaa.BD_sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ezesky on 03/03/2016.
 */
public class Fonctions_Taxi extends DataBaseWrapper {


    //FONCTION 6
    public List<Woroworo> getAllTaxi() {
        List<Woroworo> l_bus = new ArrayList<>();

        // declaration de la requete
        String selectQuery = "SELECT  lib_woro , depart_woro , terminus_woro , type_woro_woro , prix_woro  FROM " + WOROWORO +"" +
                " ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Woroworo w = new Woroworo();
                w.setLIB_WORO(cursor.getString(0));
                w.setDEPART_WORO(cursor.getString(1));
                w.setTERMINUS_WORO(cursor.getString(2));
                w.setTYPE_WORO_WORO(cursor.getString(3));
                w.setPRIX_WORO(cursor.getString(4));


                // Adding contact to list
                l_bus.add(w);
            } while (cursor.moveToNext());
        }
        // return contact list
        return l_bus;
    }


 /*POUR LES GBAKA
        -------------------------------------------------------------------------------------------------------
        */





//-----------------------------------------------------------------------------------------------------------------------
    //FONCTION POUR AFFICHER LES INFOS CONCERNANT UN GBAKA

    public Woroworo chercheTaxi (String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db. rawQuery( "select lib_woro , depart_woro , terminus_woro , detail_woro  , prix_woro , type_woro_woro  FROM woroworo "
                + " where lib_woro=? ", new String[] {id} ) ;
        Woroworo woro = new Woroworo();


        if (c != null) {
            c.moveToFirst();

            woro.setLIB_WORO(c.getString(0));
            woro.setDEPART_WORO(c.getString(1));
            woro.setTERMINUS_WORO(c.getString(2));
            woro.setDETAIL_WORO(c.getString(3));
            woro.setPRIX_WORO(String.valueOf(c.getInt(4)));
            woro.setTYPE_WORO_WORO(c.getString(5));



        }


        return woro;

    }
    public Woroworo chercheTaxi2 (String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db. rawQuery( "select lib_woro , depart_woro , terminus_woro , detail_woro  , prix_woro , type_woro_woro  FROM woroworo "
                + " where id_woro=? ", new String[] {id} ) ;
        Woroworo woro = new Woroworo();


        if (c != null) {
            c.moveToFirst();

            woro.setLIB_WORO(c.getString(0));
            woro.setDEPART_WORO(c.getString(1));
            woro.setTERMINUS_WORO(c.getString(2));
            woro.setDETAIL_WORO(c.getString(3));
            woro.setPRIX_WORO(String.valueOf(c.getInt(4)));
            woro.setTYPE_WORO_WORO(c.getString(5));



        }


        return woro;

    }
    //------------------------------------------------ FIN ----------------------------------------------------------------------


//-----------------------------------------------------------------------------------------------------------------------
    //FONCTION POUR AFFICHER TOUT LES GBAKAS A PARTIR D UN LIEU DONNNE

    public List <String> AllSilloner(String id) {


        List<String> lieu_taxi = new ArrayList<>();

        // declaration de la requete
        String selectQuery = "SELECT  woroworo.lib_woro FROM " + WOROWORO +"" +
                " INNER JOIN silloner " +
                " ON silloner.id_woro=woroworo.id_woro " +
                " INNER JOIN LIEU " +
                " " +
                " ON silloner.id_lieu=lieu.id_lieu " +
                " WHERE lieu.nom_lieu LIKE ? ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{id});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do{
                System.out.println("ON ES DEDANS ");
                // Adding contact to list
                lieu_taxi.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // return contact list
        return lieu_taxi;

    }


    //------------------------------------------------ FIN ----------------------------------------------------------------------









    //-----------------------------------------------------------------------------------------------------------------------
    //FONCTION POUR çççç
    public List<Silloner> req_etape_1_Silloner(String dep ,String arr) {
        List<Silloner> res_trouvés = new ArrayList<>();

        // declaration de la requete



        String selectQuery = "SELECT  silloner."+  ID_WORO+"   FROM " + SILLONER +"" +
                " INNER JOIN "+LIEU+"" +
                " ON silloner."+ID_LIEU+"=lieu."+ID_LIEU+"" +
                " WHERE "+NOM_LIEU+" = ? AND silloner."+ID_WORO+" " +
                "IN  " +
                "( SELECT  silloner."+  ID_WORO+"   FROM " + SILLONER  +"" +
                " INNER JOIN "+LIEU+"" +
                " ON silloner."+ID_LIEU+"=lieu."+ID_LIEU+"" +
                " WHERE "+NOM_LIEU+" =  ?  ) " ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{dep,arr});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {
                Silloner w = new Silloner();
                w.setID_WORO(cursor.getString(0));



                // Adding contact to list
                res_trouvés.add(w);
            } while (cursor.moveToNext());
        }else{

        }

        db.close();
        // return contact list
        return res_trouvés;
    }

//------------------------------------------------ FIN ----------------------------------------------------------------------

    public List<Woroworo> req_etape_2_Silloner(String nom_lieu) {
        List<Woroworo> res_trouvés = new ArrayList<>();

        // declaration de la requete
        String selectQuery = "SELECT  woroworo."+  LIB_WORO+"   FROM " + WOROWORO +"" +
                " INNER JOIN "+SILLONER+"" +
                " ON woroworo."+ID_WORO+"=silloner."+ID_LIEU+"" +
                " INNER JOIN lieu" +
                " ON silloner."+ID_LIEU+" = lieu."+ID_LIEU+"" +
                " " +
                " WHERE lieu."+NOM_LIEU+" LIKE ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{nom_lieu});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            issue_requete_2_rouler=true;
            do {
                Woroworo w = new Woroworo();
                w.setLIB_WORO(cursor.getString(0));


                // Adding contact to list
                res_trouvés.add(w);
            } while (cursor.moveToNext());
        }else{
            issue_requete_2_rouler=false;
        }
        db.close();
        // return contact list
        return res_trouvés;
    }





    //REQUETE DE RECHERCHE 1
    //1- On verifie d'abord si le depart selectionné correspond a un depart d un taxi ,idem pour le terminus
    //2- Si cest pas le cas  l' on verifie si le depart correspond a un gbaka

    public List<String> Chercher_Taxi(String dep,String arr){
        List<String> resultat_final=new ArrayList<>();
        itineraire_final="";
        combinaison_possibles="";
        int nbre_vehicules_a_emprunpter=0;


        //1ere REQUETE :
        List <Silloner>res1_taxi = req_etape_1_Silloner(dep, arr);


        if (res1_taxi!=null) {
            for (Silloner pp : res1_taxi) {
                resultat_final.add(pp.getID_WORO());
            }
        }
            nbre_vehicules_a_emprunpter++;
            itineraire_final=nbre_vehicules_a_emprunpter+"  seul véhicule à emprunter de  "+dep+" à =>> "+arr+"  ";
            combinaison_possibles="Pas de decompositions à faire .!!!";






        return resultat_final;
    }







    public Fonctions_Taxi(Context context){
        super(context);
    }
}
