package com.ezesky.likaa.BD_sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ezesky on 03/03/2016.
 */
public class Fonctions_Bus extends DataBaseWrapper {




     /*FONCTIONS
  POUR LES BUS
  --------------------------------------------------------------------------------------------------------------------
   */


    //FONCTION 1
    //REQUETE DE RECHERCHE 1
    //1- On verifie d'abord si le depart sélectionné correspond à un depart d'un bus ,idem pour le terminus
    //2- Si cest pas le cas  l' on verifie si le depart ou le terminus  est desservi par une ligne de bus
    //3- Dans le cas contraire l'on cherche des rues intermediaires afin de reecrire le trajet et ainsi savoir combien de ligne de bus utiliser pour aller du depart à l'arrivée

    public List<String> Chercher_Bus(String dep, String arr){
        List<String> resultat_final=new ArrayList<>();
        itineraire_final="";
        combinaison_possibles="";
        int nbre_vehicules_a_emprunpter=0;
        boolean issue_dep_ter;
        boolean issue_ter_dep;






        //1ere REQUETE : ON RECHERCHE LES LIGNES DE BUS QUI PASSENT PAR LA RUE DE DEPART ET LA RUE D ARRIVEE
        List <Passer_par>res1_bus = req_etape_1_Bus(dep, arr);


        if (res1_bus.size()>0){

            System.out.println("on passe a la 1ere tentative !!!!!!!!!!!!!!!!!");
            for (Passer_par pp: res1_bus) {
                resultat_final.add(pp.getID_BUS());

            }
            nbre_vehicules_a_emprunpter++;
            itineraire_final=nbre_vehicules_a_emprunpter+"  seul véhicule à emprunter de  "+dep+" à =>> "+arr+"  ";
            combinaison_possibles="Pas de decompositions à faire .!!!";
        }
        else //DANS LE CAS CONTRAIRE NOUS PASSONS A LA 2E REQUETE
        {
            System.out.println("on passe a la 2e tentative !!!!!!!!!!!!!!!!!");

            //2eme REQUETE : 1.) ON RECHERCHE LES LIGNES DE BUS QUI PASSENT PAR LE DEPART ET LA GARE NORD
            //ENSUITE CELLES QUI PASSENT PAR LA GARE NORD ET LE TERMINUS
            //CES RESULTATS NOUS PERMETTRONT DE SAVOIR SI IL EST POSSIBLE DE RALIER LE DEPART ET L ARRIVEE EN UTILISANT COMME RUE INTERMEDIAIRE LA GARE NORD
            List <Passer_par>res2_bus_pour_depart= essai(dep,"GARE NORD ADJAME");
            List <Passer_par>res2_bus_pour_arrivee= essai(arr,"GARE NORD ADJAME");
            int indice_depart=1;
            int indice_arrivee=1;


            if (res2_bus_pour_depart.size()>0 && res2_bus_pour_arrivee.size()>0){//Si NOUS OBTENONS DES RESULTATS
                nbre_vehicules_a_emprunpter+=2;
                for (Passer_par b : res2_bus_pour_depart) {
                    resultat_final.add(b.getID_BUS());
                    System.out.println("pour depart  n° "+indice_depart+" ="+ b.getID_BUS());
                    indice_depart++;
                }

                for (Passer_par b : res2_bus_pour_arrivee) {
                    resultat_final.add(b.getID_BUS());
                    System.out.println("pour arrivee  n° "+indice_arrivee+" ="+ b.getID_BUS());
                    indice_arrivee++;
                }

                itineraire_final = nbre_vehicules_a_emprunpter + " véhicules à emprunter de  " + dep + " à =>> " + arr + " ";

                String conbinaison="";

                for (Passer_par b : res2_bus_pour_depart){


                    for(Passer_par b1 : res2_bus_pour_arrivee){
                        System.out.println(Trouver_rue_intermediaire(b.getID_BUS(),b1.getID_BUS()));
                        conbinaison+=" "+b.getID_BUS()+" ==> "+b1.getID_BUS()+" ,";
                    }
                }
                conbinaison+="@";
                combinaison_possibles=""+((indice_depart-1)*(indice_arrivee-1))+" combinaison(s) possible(s) telle(s) que :"+conbinaison.replace(",@","") +".";
            }else{
                //2eme REQUETE : 2.) ON RECHERCHE LES LIGNES DE BUS QUI PASSENT PAR LE DEPART ET LA GARE SUD
                //ENSUITE CELLES QUI PASSENT PAR LA GARE NORD ET LE TERMINUS
                //CES RESULTATS NOUS PERMETTRONT DE SAVOIR SI IL EST POSSIBLE DE RALIER LE DEPART ET L ARRIVEE EN UTILISANT COMME RUE INTERMEDIAIRE LA GARE SUD

                res2_bus_pour_depart= essai(dep,"GARE SUD PLATEAU");
                res2_bus_pour_arrivee= essai(arr,"GARE SUD PLATEAU");



                if (res2_bus_pour_depart.size()>0 && res2_bus_pour_arrivee.size()>0) {//Si NOUS OBTENONS DES RESULTATS
                    nbre_vehicules_a_emprunpter += 2;
                    for (Passer_par b : res2_bus_pour_depart) {
                        resultat_final.add(b.getID_BUS());
                        System.out.println("pour depart  n° " + indice_depart + " =" + b.getID_BUS());
                        indice_depart++;
                    }

                    for (Passer_par b : res2_bus_pour_arrivee) {
                        resultat_final.add(b.getID_BUS());
                        System.out.println("pour arrivee  n° " + indice_arrivee + " =" + b.getID_BUS());
                        indice_arrivee++;
                    }


                    String conbinaison = "";

                    for (Passer_par b : res2_bus_pour_depart) {


                        for (Passer_par b1 : res2_bus_pour_arrivee) {


                            System.out.println(Trouver_rue_intermediaire(b.getID_BUS(),b1.getID_BUS()));
                            conbinaison += " " + b.getID_BUS() + " ==> " + b1.getID_BUS() + " ,";
                        }
                    }
                    conbinaison += "@";
                    combinaison_possibles = "" + ((indice_depart - 1) * (indice_arrivee - 1)) + " combinaison(s) possible(s) telle(s) que :" + conbinaison.replace(",@", "") + ".";
                    itineraire_final = nbre_vehicules_a_emprunpter + " véhicules à emprunpter de  " + dep + " à =>> " + arr + " ";


                }}

        }

        return resultat_final;
    }
































    //Autres fonctions
    public List Bus2(String dep,String arr){
        ArrayList<String> resultat_final=new ArrayList<>();
        //Declarations des variables;
        int nbre_rues_intermediaire=0;
        boolean arreter_procedure=false;
        int nbre_vehicules_a_emprunpter=0;
        ArrayList<String>rue_intermediaire=new ArrayList<>();





        String tmp_dep=dep;
        String tmp_arr=arr;

        //ON CHERCHE TOUTES LES LIGNES DE BUS PASSANT UNIQUEMENT PAR DEPART
        ArrayList<String>depart=LigneBus(dep);

        //ON CHERCHE TOUTES LES LIGNES DE BUS PASSANT UNIQUEMENT PAR ARRIVEE
        ArrayList<String>arrivee=LigneBus(arr);
        int index=0;




        do{
            //1ere REQUETE : ON RECHERCHE LES LIGNES DE BUS QUI PASSENT PAR LA RUE DE DEPART ET LA RUE D ARRIVEE
            List <Passer_par>res1_bus = req_etape_1_Bus(depart.get(index), arrivee.get(index));


            if (res1_bus.size()>0){

                System.out.println("on passe a la 1ere tentative !!!!!!!!!!!!!!!!!");
                for (Passer_par pp: res1_bus) {
                    resultat_final.add(pp.getID_BUS());

                }
                nbre_vehicules_a_emprunpter++;
                itineraire_final=nbre_vehicules_a_emprunpter+"  seul véhicule à emprunter de  "+tmp_dep+" à =>> "+tmp_arr+"  ";
                combinaison_possibles="Pas de decompositions à faire .!!!";



                arreter_procedure=true;
            }

            else{


                //ON PASSE A LA RECHERCHE DE RUE INTERMEDIAIRES



                nbre_rues_intermediaire++;
                //ON CHERCHE TOUTES LES LIGNES DE BUS PASSANT UNIQUEMENT PAR DEPART
                ArrayList<String>ligne_pour_depart=LigneBus(depart.get(index));

                //ON CHERCHE TOUTES LES LIGNES DE BUS PASSANT UNIQUEMENT PAR ARRIVEE
                ArrayList<String>ligne_pour_arrivee=LigneBus(arrivee.get(index));



                /*CETTE FOIS CI ON PREND UNE LIGNE DE ligne_pour_depart ET UNE LIGNE DE ligne_pour_arivee
                    ON CHERCHE UNE RUE PARMIS TOUTES LES RUES DE ligne_pour_depart QUI SOIT PARMIS LES RUES DE ligne_pour_arrivee
                    TANT QU ON NE LE TROUVE PAS ON CONTINUE EN COMBINANT UNE LIGNE DE depart et arrivée EN BOUCLE
                */

                for (String ligne_dep:ligne_pour_depart){
                    for(String ligne_arr:ligne_pour_arrivee){


                        // declaration de la requete
                        String selectQuery = "SELECT nom_rue  FROM " +
                                "(SELECT rue.nom_rue FROM passer_par " +
                                "INNER JOIN rue " +
                                "ON passer_par.id_rue=rue.id_rue " +
                                " WHERE id_bus = ? ) " +
                                "WHERE nom_rue IN (SELECT rue.nom_rue FROM passer_par " +
                                "INNER JOIN rue " +
                                "ON passer_par.id_rue=rue.id_rue " +
                                " WHERE id_bus = ? )" ;


                        SQLiteDatabase db = this.getWritableDatabase();
                        Cursor cursor = db.rawQuery(selectQuery, new String[]{ligne_dep,ligne_arr});
                        System.out.println("ON COMPARE AVEC LA LIGNE DE BUS " +ligne_dep +" ET LA LIGNE DE BUS "+ligne_arr);
                        System.out.println("ON LANCE LA REQUETE");

                        // looping through all rows and adding to list
                        if (cursor.moveToFirst()) {
                            arreter_procedure=true;
                            //ON AJOUTE LES LIGNE EN EVITANT LES DOUBLONS
                            System.out.println("RESULTAT TROUVER AVEC LA LIGNE DE BUS "+ligne_dep +"ET LA LIGNE DE BUS"+ligne_arr);


                            boolean trouvé1=false;
                            boolean trouvé2=false;

                            //LIGNE DEPART
                            for (String p:resultat_final){
                                if (p.equals(ligne_dep)){
                                    trouvé1=true;
                                }
                            }

                            if (trouvé1==false){
                                resultat_final.add(ligne_dep);
                            }


                            //LIGNE ARRIVEE
                            for (String p:resultat_final){
                                if (p.equals(ligne_arr)){
                                    trouvé2=true;
                                }
                            }

                            if (trouvé2==false){
                                resultat_final.add(ligne_arr);
                            }





                            int bb=0;


                            do {
                                bb++;
                                System.out.println("RUE INTERMEDIAIRE "+bb+"TROUVEE = "+cursor.getString(0));
                                rue_intermediaire.add(cursor.getString(0));




                            } while (cursor.moveToNext());
                            System.out.println(" ON PASSE A UN AUTRE");
                            System.out.println("--------------------------------------------------------------------");


                            //ON REECRIT LE TRAJET
                            String trajet="";
                            for(String p:rue_intermediaire){
                                trajet+=p+" =>> ";
                            }

                            itineraire_final=nbre_vehicules_a_emprunpter+"  seul véhicule à emprunter de  "+dep+" à =>> "+arr+"  ";
                            combinaison_possibles="Pas de decompositions à faire .!!!";
                            itineraire_final = (Integer.valueOf(nbre_rues_intermediaire+2)) + " véhicules à emprunpter de  " +trajet+ " ";


                        }else{
                            System.out.println("PAS DE RESULTAT ON PASSE A UN AUTRE");
                            System.out.println("--------------------------------------------------------------------");
                            nbre_rues_intermediaire++;



                        }
                        db.close();
                    }
                }







            }


        }while(arreter_procedure!=true);


        return resultat_final;
    }





    private void All_nom_rue_dep(String ligne,String rue1){


        // declaration de la requete
        String selectQuery = "SELECT  rue.nom_rue FROM passer_par " +
                " INNER JOIN RUE " +
                " ON passer_par.id_rue=rue.id_rue " +
                " WHERE passer_par.id_bus = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{ligne});
        Toutes_les_Rues_dep.add(rue1);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {
                if (cursor.getString(0).equals(rue1)){

                }else{
                    Toutes_les_Rues_dep.add(cursor.getString(0));
                }
            } while (cursor.moveToNext());
        }else{

        }
        db.close();
    }

    private void All_nom_rue_arr(String ligne,String rue1){


        // declaration de la requete
        String selectQuery = "SELECT  rue.nom_rue FROM passer_par " +
                " INNER JOIN RUE " +
                " ON passer_par.id_rue=rue.id_rue " +
                " WHERE passer_par.id_bus = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{ligne});
        Toutes_les_Rues_arr.add(rue1);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {
                if (cursor.getString(0).equals(rue1)){

                }else{
                    Toutes_les_Rues_arr.add(cursor.getString(0));
                }

            } while (cursor.moveToNext());
        }else{

        }
        db.close();
    }


    public ArrayList<String> LigneBus(String rue){
        ArrayList<String> res_trouvés = new ArrayList<>();

        // declaration de la requete
        String selectQuery = "SELECT  passer_par."+  ID_BUS+"   FROM " + PASSER_PAR +"" +
                " INNER JOIN "+RUE+"" +
                " ON passer_par."+ID_RUE+"=rue."+ID_RUE+"" +
                " WHERE "+NOM_RUE+" = ? " ;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{rue});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            issue_requete_1_passer_par=true;
            do {

                res_trouvés.add(cursor.getString(0));




            } while (cursor.moveToNext());
        }else{

        }
        db.close();

        // return contact list
        return res_trouvés;
    }



    //FONCTION 2
    public List<Passer_par> essai(String depart,String rue_intermediaire) {
        List<Passer_par> res_trouvés = new ArrayList<Passer_par>();



        // declaration de la requete
        String selectQuery = "SELECT  passer_par."+  ID_BUS+"   FROM " + PASSER_PAR +"" +
                " INNER JOIN "+RUE+"" +
                " ON passer_par."+ID_RUE+"=rue."+ID_RUE+"" +
                " WHERE "+NOM_RUE+" = '"+rue_intermediaire+"' AND passer_par."+ID_BUS+" " +
                "IN  " +
                "( SELECT  passer_par."+  ID_BUS+"   FROM " + PASSER_PAR +"" +
                " INNER JOIN "+RUE+"" +
                " ON passer_par."+ID_RUE+"=rue."+ID_RUE+"" +
                " WHERE "+NOM_RUE+" =  ?  ) " ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{depart});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            issue_requete_2_passer_par=true;
            do {

                Passer_par passer_par = new Passer_par();
                passer_par.setID_BUS(cursor.getString(0));



                // Adding contact to list
                res_trouvés.add(passer_par);
            } while (cursor.moveToNext());
        }else{
            issue_requete_2_passer_par=false;
        }
        db.close();
        // return contact list
        return res_trouvés;
    }

    //FONCTION 3
    public List<Passer_par> req_etape_1_Bus(String dep ,String arr) {
        List<Passer_par> res_trouvés = new ArrayList<>();

        // declaration de la requete
        String selectQuery = "SELECT  passer_par."+  ID_BUS+"   FROM " + PASSER_PAR +"" +
                " INNER JOIN "+RUE+"" +
                " ON passer_par."+ID_RUE+"=rue."+ID_RUE+"" +
                " WHERE "+NOM_RUE+" = ? AND passer_par."+ID_BUS+" " +
                "IN(  " +
                "SELECT  passer_par."+  ID_BUS+"   FROM " + PASSER_PAR +"" +
                " INNER JOIN "+RUE+"" +
                " ON passer_par."+ID_RUE+"=rue."+ID_RUE+"" +
                " WHERE "+NOM_RUE+"  = ? )" ;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{dep,arr});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            issue_requete_1_passer_par=true;
            do {
                Passer_par bus = new Passer_par();
                bus.setID_BUS(cursor.getString(0));



                // Adding contact to list
                res_trouvés.add(bus);
            } while (cursor.moveToNext());
        }else{
            issue_requete_1_passer_par=false;
        }
        db.close();

        // return contact list
        return res_trouvés;
    }




    //FONCTION 4
    public String Trouver_rue_intermediaire(String bus1,String bus2){
        String res="";
        // declaration de la requete
        String selectQuery = "SELECT  rue."+  NOM_RUE+"   FROM " + RUE +"" +
                " INNER JOIN "+PASSER_PAR+"" +
                " ON passer_par."+ID_RUE+"=rue."+ID_RUE+"" +
                " WHERE "+ID_BUS+" = ? AND rue."+NOM_RUE+" " +
                "IN(  " +
                " SELECT  rue."+  NOM_RUE+"   FROM " + RUE +"" +
                " INNER JOIN "+PASSER_PAR+"" +
                " ON passer_par."+ID_RUE+"=rue."+ID_RUE+"" +
                " WHERE "+ID_BUS+" = ? )" ;


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{bus1,bus2});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {

                res+=""+cursor.getString(0)+" ";
            } while (cursor.moveToNext());
        }


        db.close();

        return res;
    }



    //FONCTION 5
    public Bus chercheBus (String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db. rawQuery( "select " + ID_BUS + " as id,"+DEPART_BUS+", "+TERMINUS_BUS+",type_bus , "+DETAIL_BUS+ " from " + BUS
                + " where id_bus=? ", new String[] {id} ) ;
        Bus bus = new Bus();
        if (c != null) {
            c.moveToFirst();

            bus.setID_BUS(c.getString(0));
            bus.setDEPART_BUS(c.getString(1));
            bus.setTERMINUS_BUS(c.getString(2));
            bus.setTYPE_BUS(c.getString(3));
            bus.setDETAIL_BUS(c.getString(4));


        }


        return bus;

    }


    //FONCTION 6
    public List<Bus> getAllRows() {
        List<Bus> l_bus = new ArrayList<Bus>();

        // declaration de la requete
        String selectQuery = "SELECT  *  FROM " + BUS +"" +
                " ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Bus bus = new Bus();
                bus.setID_BUS(cursor.getString(0));
                bus.setDEPART_BUS(cursor.getString(1));
                bus.setTERMINUS_BUS(cursor.getString(2));
                bus.setTYPE_BUS(cursor.getString(4));


                // Adding contact to list
                l_bus.add(bus);
            } while (cursor.moveToNext());
        }
        // return contact list
        return l_bus;
    }








    //afficher toutes les lignes
    public List <Passer_par> AllPasser_par(String id) {


        List<Passer_par> rue_bus = new ArrayList<Passer_par>();

        // declaration de la requete
        String selectQuery = "SELECT  id_bus FROM " + PASSER_PAR +"" +
                " INNER JOIN rue " +
                " ON passer_par.id_rue=rue.id_rue " +
                " WHERE rue.nom_rue LIKE ?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{id});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Passer_par passer_par = new Passer_par();
                passer_par.setID_BUS(cursor.getString(0));


                // Adding contact to list
                rue_bus.add(passer_par);
            } while (cursor.moveToNext());
        }
        // return contact list
        return rue_bus;

    }











    //afficher toutes les lignes
    public List <String> AllRue() {


        List<String> rue_all = new ArrayList<>();

        // declaration de la requete
        String selectQuery = "SELECT  nom_rue FROM " + RUE +"" ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                rue_all.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // return contact list
        return rue_all;

    }










    public Fonctions_Bus(Context context){
        super(context);

    }
}
