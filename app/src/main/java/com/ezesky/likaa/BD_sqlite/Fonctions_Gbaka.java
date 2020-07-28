package com.ezesky.likaa.BD_sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ezesky on 03/03/2016.
 */
public class Fonctions_Gbaka extends DataBaseWrapper {

  /*POUR LES GBAKA
        -------------------------------------------------------------------------------------------------------
        */


    // FONCTION 1
    public List<String> Chercher_Gbaka(String dep, String arr){
        List<String> resultat_final=new ArrayList<>();
        itineraire_final="";
        combinaison_possibles="";
        int nbre_vehicules_a_emprunpter=0;



        //1ere REQUETE :
        List <Rouler>res1_gbaka = req_etape_1_Rouler(dep, arr);

        System.out.println("on passe a la 1ere tentative !!!!!!!!!!!!!!!!!");
        if (res1_gbaka.size()>0){
            for (Rouler pp: res1_gbaka) {
                resultat_final.add(pp.getID_GBAKA());
            }
            nbre_vehicules_a_emprunpter++;
            itineraire_final=nbre_vehicules_a_emprunpter+"  seul véhicule à emprunter de  "+dep+" à =>> "+arr+"  ";
            combinaison_possibles="Pas de decompositions à faire .!!!";
        } else //DANS LE CAS CONTRAIRE NOUS PASSONS A LA 2E REQUETE
        {
            List <Gbaka>res2_bus_pour_depart= req_etape_2_Rouler(dep);
            List <Gbaka>res2_bus_pour_arrivee= req_etape_2_Rouler(arr);
            int indice_depart=1;
            int indice_arrivee=1;


            if (res2_bus_pour_depart.size()>0 && res2_bus_pour_arrivee.size()>0){//Si NOUS OBTENONS DES RESULTATS
                nbre_vehicules_a_emprunpter+=2;
                for (Gbaka b : res2_bus_pour_depart) {
                    resultat_final.add(b.getID_GBAKA());
                    System.out.println("pour depart  n° "+indice_depart+" ="+ b.getID_GBAKA());
                    indice_depart++;
                }

                for (Gbaka b : res2_bus_pour_arrivee) {
                    resultat_final.add(b.getID_GBAKA());
                    System.out.println("pour arrivee  n° "+indice_arrivee+" ="+ b.getID_GBAKA());
                    indice_arrivee++;
                }

                itineraire_final = nbre_vehicules_a_emprunpter + " véhicules à emprunter de  " + dep + " à =>> " + arr + " ";

                String conbinaison="";

               for (Gbaka b : res2_bus_pour_depart){


                    for(Gbaka b1 : res2_bus_pour_arrivee){
                        //System.out.println(Trouver_rue_intermediaire(b.getID_BUS(),b1.getID_BUS()));
                        conbinaison+=" "+b.getLIB_GBAKA()+" ==> "+b1.getLIB_GBAKA()+" ,";
                    }
                }
                conbinaison+="@";
                combinaison_possibles=""+((indice_depart-1)*(indice_arrivee-1))+" combinaison(s) possible(s) telle(s) que :"+conbinaison.replace(",@","") +".";
            }




                  }



        return resultat_final;
    }









    public List<Rouler> requete_sql(String dep ,String arr) {
        List<Rouler> res_trouvés = new ArrayList<>();

        // declaration de la requete


        String selectQuery = "SELECT  rouler."+  ID_GBAKA+"   FROM " + ROULER +"" +
                " INNER JOIN "+LIEU+"" +
                " ON rouler."+ID_LIEU+"=lieu."+ID_LIEU+"" +
                " WHERE "+NOM_LIEU+
                "IN (?,?) " ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{dep,arr});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {
                Rouler rouler = new Rouler();
                rouler.setID_GBAKA(cursor.getString(0));
                //rouler.setID_LIEU(cursor.getString(1));


                // Adding contact to list
                res_trouvés.add(rouler);
            } while (cursor.moveToNext());
        }else{

        }
        db.close();
        // return contact list
        return res_trouvés;
    }






    //-----------------------------------------------------------------------------------------------------------------------
    //FONCTION POUR AFFICHER LES INFOS CONCERNANT UN GBAKA

    public Gbaka chercheGbaka (String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db. rawQuery( "select lib_gbaka , depart_gbaka , terminus_gbaka , detail_gbaka , prix_gbaka_fixe, prix_periode FROM gbaka "
                + " where lib_gbaka=? ", new String[] {id} ) ;
        Gbaka gb = new Gbaka();
        if (c != null) {
            c.moveToFirst();

            gb.setLIB_GBAKA(c.getString(0));
            gb.setDEPART_GBAKA(c.getString(1));
            gb.setTERMINUS_GBAKA(c.getString(2));
            gb.setDETAIL_GBAKA(c.getString(3));
            gb.setPRIX_GBAKA_FIXE(c.getString(4));
            gb.setPRIX_PERIODE(c.getString(5));


        }


        return gb;

    }

    public Gbaka chercheGbaka2 (String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db. rawQuery( "select lib_gbaka , depart_gbaka , terminus_gbaka , detail_gbaka , prix_gbaka_fixe, prix_periode FROM gbaka "
                + " where id_gbaka=? ", new String[] {id} ) ;
        Gbaka gb = new Gbaka();
        if (c != null) {
            c.moveToFirst();

            gb.setLIB_GBAKA(c.getString(0));
            gb.setDEPART_GBAKA(c.getString(1));
            gb.setTERMINUS_GBAKA(c.getString(2));
            gb.setDETAIL_GBAKA(c.getString(3));
            gb.setPRIX_GBAKA_FIXE(c.getString(4));
            gb.setPRIX_PERIODE(c.getString(5));


        }


        return gb;

    }




    public List<Gbaka> getAllGbaka() {
        List<Gbaka> l_bus = new ArrayList<>();

        // declaration de la requete
        String selectQuery = "SELECT  lib_gbaka , depart_gbaka , terminus_gbaka , prix_gbaka_fixe , prix_periode  FROM " + GBAKA +"" +
                " ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Gbaka g = new Gbaka();
                g.setLIB_GBAKA(cursor.getString(0));
                g.setDEPART_GBAKA(cursor.getString(1));
                g.setTERMINUS_GBAKA(cursor.getString(2));
                g.setPRIX_GBAKA_FIXE(cursor.getString(3));
                g.setPRIX_PERIODE(cursor.getString(4));


                // Adding contact to list
                l_bus.add(g);
            } while (cursor.moveToNext());
        }
        // return contact list
        return l_bus;
    }
    //------------------------------------------------ FIN ----------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------------------
    //FONCTION POUR çççç
    public List<Rouler> req_etape_1_Rouler(String dep ,String arr) {
        List<Rouler> res_trouvés = new ArrayList<>();

        // declaration de la requete


        String selectQuery = "SELECT  rouler."+  ID_GBAKA+"   FROM " + ROULER +"" +
                " INNER JOIN "+LIEU+"" +
                " ON rouler."+ID_LIEU+"=lieu."+ID_LIEU+"" +
                " WHERE "+NOM_LIEU+" = ? AND rouler."+ID_GBAKA+" " +
                "IN  " +
                "( SELECT  rouler."+  ID_GBAKA+"   FROM " + ROULER +"" +
                " INNER JOIN "+LIEU+"" +
                " ON rouler."+ID_LIEU+"=lieu."+ID_LIEU+"" +
                " WHERE "+NOM_LIEU+" =  ?  ) " ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{dep,arr});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {
                Rouler rouler = new Rouler();
                rouler.setID_GBAKA(cursor.getString(0));
                //rouler.setID_LIEU(cursor.getString(1));


                // Adding contact to list
                res_trouvés.add(rouler);
            } while (cursor.moveToNext());
        }else{

        }
        db.close();
        // return contact list
        return res_trouvés;
    }
//------------------------------------------------ FIN ----------------------------------------------------------------------


    //-----------------------------------------------------------------------------------------------------------------------


    public List<Gbaka> req_etape_2_Rouler(String nom_lieu) {
        List<Gbaka> res_trouvés = new ArrayList<>();

        // declaration de la requete
        String selectQuery = "SELECT  gbaka."+  LIB_GBAKA+",gbaka." +ID_GBAKA+"   FROM " + ROULER +"" +
                " INNER JOIN "+LIEU+"" +
                " ON rouler."+ID_LIEU+"=lieu."+ID_LIEU+"" +
                " " +
                " INNER JOIN "+GBAKA+"" +
                " ON rouler."+ID_GBAKA+"= gbaka."+ID_GBAKA+"" +
                " WHERE lieu."+NOM_LIEU+" = ? ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{nom_lieu});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            issue_requete_1_silloner=true;
            do {
                Gbaka rouler = new Gbaka();
                rouler.setLIB_GBAKA(cursor.getString(0));
               rouler.setID_GBAKA(cursor.getString(1));


                // Adding contact to list
                res_trouvés.add(rouler);
            } while (cursor.moveToNext());
        }else{
            issue_requete_1_silloner=false;
        }
        db.close();
        // return contact list
        return res_trouvés;
    }
    //------------------------------------------------ FIN ----------------------------------------------------------------------


    public List <String> AllRouler(String id) {


        List<String> lieu_taxi = new ArrayList<>();

        // declaration de la requete
        String selectQuery = "SELECT  gbaka.lib_gbaka FROM " + GBAKA +"" +
                " INNER JOIN rouler " +
                " ON rouler.id_gbaka=gbaka.id_gbaka " +
                " INNER JOIN LIEU " +
                " " +
                " ON rouler.id_lieu=lieu.id_lieu " +
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




    public Fonctions_Gbaka(Context context){
        super(context);

    }

}
