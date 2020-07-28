package com.ezesky.likaa.BD_sqlite;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DataBaseWrapper extends SQLiteOpenHelper {

 /*Declaration des colonnes des tables*/ 
public static String PASSER_PAR = "passer_par" ;
public static String WOROWORO = "woroworo" ;
public static String SILLONER = "silloner" ;
public static String ROULER = "rouler" ;

    public static String RUE = "rue" ;
    public static String LIEU = "lieu" ;
    public static String GBAKA = "gbaka" ;
    public static String COMMUNE = "commune" ;
    public static String BUS = "bus" ;
    public static String QUARTIER = "quartier" ;
    public static String ITINERAIRE="itineraire";



public static final String ID_LIEU = "id_lieu" ;
public static final String ID_QUARTIER = "id_quartier" ;
public static final String NOM_LIEU = "nom_lieu" ;
public static final String IMAGE_LIEU = "image_lieu" ;
public static final String DETAIL_LIEU = "detail_lieu" ;
public static final String ID_GBAKA = "id_gbaka" ;
public static final String COUT_TRANSPORT_GBAKA = "cout_transport_gbaka" ;
public static final String ID_WORO = "id_woro" ;
public static final String COUT_TRANSPORT_WORO = "cout_transport_woro" ;
public static final String LIB_GBAKA = "lib_gbaka" ;
public static final String DETAIL_GBAKA = "detail_gbaka" ;
public static final String DEPART_GBAKA = "depart_gbaka" ;
public static final String TERMINUS_GBAKA = "terminus_gbaka" ;
public static final String PRIX_GBAKA_FIXE = "prix_gbaka_fixe" ;
public static final String PRIX_PERIODE = "prix_periode" ;
public static final String LIB_WORO = "lib_woro" ;
public static final String DEPART_WORO = "depart_woro" ;
public static final String TERMINUS_WORO = "terminus_woro" ;
public static final String DETAIL_WORO = "detail_woro" ;
public static final String TYPE_WORO_WORO = "type_woro_woro" ;
public static final String PRIX_WORO = "prix_woro" ;
public static final String ID_BUS = "id_bus" ;
public static final String DEPART_BUS = "depart_bus" ;
public static final String TERMINUS_BUS = "terminus_bus" ;
public static final String DETAIL_BUS = "detail_bus" ;
public static final String TYPE_BUS = "type_bus" ;
public static final String ID_COMMUNE = "id_commune" ;
public static final String NOM_COMMUNE = "nom_commune" ;
public static final String SITUATION_GEO_COMMUNE = "situation_geo_commune" ;
public static final String ID_RUE = "id_rue" ;
public static final String NOM_QUARTIER = "nom_quartier" ;
public static final String NOM_RUE = "nom_rue" ;
public static final String IMAGE_RUE = "image_rue" ;
public static final String DETAIL_RUE = "detail_rue" ;


    public static final String ID_ITI="id_iti";
    public static final String LIB_ITI="lib_iti";
    public static final String MOYEN_TP_ITI="moyen_tp_iti";
    public static final String TYPE_ITI="type_iti";
    public static final String DETAIL_ITI="detail_iti";


/*Autres*/
public static final String[] LIEU_ALL_KEYS = new String[] {ID_LIEU,ID_QUARTIER,NOM_LIEU,IMAGE_LIEU,DETAIL_LIEU} ;
public static final String[] ROULER_ALL_KEYS = new String[] {ID_GBAKA,ID_QUARTIER,COUT_TRANSPORT_GBAKA} ;
public static final String[] SILLONER_ALL_KEYS = new String[] {ID_QUARTIER,ID_WORO,COUT_TRANSPORT_WORO} ;
public static final String[] GBAKA_ALL_KEYS = new String[] {ID_GBAKA,LIB_GBAKA,DETAIL_GBAKA,DEPART_GBAKA,TERMINUS_GBAKA,PRIX_GBAKA_FIXE,PRIX_PERIODE} ;
public static final String[] WOROWORO_ALL_KEYS = new String[] {ID_WORO,LIB_WORO,DEPART_WORO,TERMINUS_WORO,DETAIL_WORO,TYPE_WORO_WORO,PRIX_WORO} ;
public static final String[] BUS_ALL_KEYS = new String[] {ID_BUS,DEPART_BUS,TERMINUS_BUS,DETAIL_BUS,TYPE_BUS} ;
public static final String[] COMMUNE_ALL_KEYS = new String[] {ID_COMMUNE,NOM_COMMUNE,SITUATION_GEO_COMMUNE} ;
public static final String[] PASSER_PAR_ALL_KEYS = new String[] {ID_BUS,ID_RUE} ;
public static final String[] QUARTIER_ALL_KEYS = new String[] {ID_QUARTIER,ID_COMMUNE,NOM_QUARTIER} ;
public static final String[] RUE_ALL_KEYS = new String[] {ID_RUE,ID_QUARTIER,NOM_RUE,IMAGE_RUE,DETAIL_RUE} ;




    boolean issue_requete_1_silloner;
    boolean issue_requete_2_rouler;


    public String itineraire_final="";
    public String combinaison_possibles="";

    boolean issue_requete_1_passer_par;
    boolean issue_requete_2_passer_par;

    ArrayList<String> Toutes_les_Rues_dep=new ArrayList<>();
    ArrayList<String> Toutes_les_Rues_arr=new ArrayList<>();


   public  String tmp_moy_tp_iti="";


/*declaration du nom de la base sqlite et de sa version*/
    
    private static final String DATABASE_NAME = "likaa_full.db";
 private static final int DATABASE_VERSION = 1;

 // REQUETES DE CREATION DES TABLES 
private static final String DATABASE_CREATE_LIEU = "create table lieu (id_lieu text primary key , id_quartier text  , nom_lieu text  , image_lieu text  , detail_lieu text   )" ;

    private static final String DATABASE_CREATE_ROULER = "create table rouler (id_gbaka text  ,id_lieu text  ,cout_transport_gbaka integer ,primary key(id_gbaka,id_lieu))" ;

private static final String DATABASE_CREATE_SILLONER = "create table silloner (id_lieu text  ,id_woro text  ,cout_transport_woro integer ,  primary key(id_lieu,id_woro)  )" ;

private static final String DATABASE_CREATE_GBAKA = "create table gbaka (id_gbaka text primary key ,lib_gbaka text  ,detail_gbaka text  ,depart_gbaka text  ,terminus_gbaka text  ,prix_gbaka_fixe text  ,prix_periode text   )" ; 

private static final String DATABASE_CREATE_WOROWORO = "create table woroworo (id_woro text primary key ,lib_woro text  ,depart_woro text  ,terminus_woro text  ,detail_woro text  ,type_woro_woro text  ,prix_woro integer   )" ; 

private static final String DATABASE_CREATE_BUS = "create table bus (id_bus text primary key ,depart_bus text  ,terminus_bus text  ,detail_bus text  ,type_bus text   )" ; 

private static final String DATABASE_CREATE_COMMUNE = "create table commune (id_commune text primary key ,nom_commune text  ,situation_geo_commune text   )" ; 

private static final String DATABASE_CREATE_PASSER_PAR = "create table passer_par (id_bus text  ,id_rue text , primary key(id_bus , id_rue ))" ;

private static final String DATABASE_CREATE_QUARTIER = "create table quartier (id_quartier text primary key ,id_commune text  ,nom_quartier text   )" ; 

private static final String DATABASE_CREATE_RUE = "create table rue (id_rue text primary key ,id_quartier text  ,nom_rue text  ,image_rue text  ,detail_rue text   )" ;

    private static final String DATABASE_CREATE_ITINERAIRE = "create table itineraire ( id_iti text ,lib_iti text ,moyen_tp_iti text ,type_iti text ,detail_iti text , primary key (id_iti)) ";



 public DataBaseWrapper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {	
    	//CREATION
db.execSQL(DATABASE_CREATE_LIEU );
db.execSQL(DATABASE_CREATE_ROULER );
db.execSQL(DATABASE_CREATE_SILLONER );
db.execSQL(DATABASE_CREATE_GBAKA );
db.execSQL(DATABASE_CREATE_WOROWORO );
db.execSQL(DATABASE_CREATE_BUS );
db.execSQL(DATABASE_CREATE_COMMUNE );
db.execSQL(DATABASE_CREATE_PASSER_PAR );
db.execSQL(DATABASE_CREATE_QUARTIER );
db.execSQL(DATABASE_CREATE_RUE );
        db.execSQL(DATABASE_CREATE_ITINERAIRE );



        //INSERTION

        for (String e:Data_BD2.INSERT_RUE()){
            db.execSQL(e);
        }

        for (String e:Data_BD2.INSERT_QUARTIER()){
            db.execSQL(e);
        }

        for (String e:Data_BD2.INSERT_BUS()){
            db.execSQL(e);
        }

        for (String e:Data_BD2.INSERT_PASSER_PAR()){
            db.execSQL(e);
        }

        for (String e:Data_BD2.INSERT_COMMUNE()){
            db.execSQL(e);
        }


        for (String e:Data_BD2.INSERT_ROULER()){
            db.execSQL(e);
        }


        for (String e:Data_BD2.INSERT_SILLONER()){
            db.execSQL(e);
        }

        for (String e:Data_BD2.INSERT_GBAKA()){
            db.execSQL(e);
        }

        for (String e:Data_BD2.INSERT_WOROWORO()){
            db.execSQL(e);
        }

        for (String e:Data_BD2.INSERT_LIEU()){
            db.execSQL(e);
        }

        for (String e:Data_BD2.INSERT_ITINERAIRE()){
            db.execSQL(e);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
db.execSQL("DROP TABLE IF EXISTS " +LIEU );
db.execSQL("DROP TABLE IF EXISTS " +ROULER );
db.execSQL("DROP TABLE IF EXISTS " +SILLONER );
db.execSQL("DROP TABLE IF EXISTS " +GBAKA );
db.execSQL("DROP TABLE IF EXISTS " +WOROWORO );
db.execSQL("DROP TABLE IF EXISTS " +BUS );
db.execSQL("DROP TABLE IF EXISTS " +COMMUNE );
db.execSQL("DROP TABLE IF EXISTS " +PASSER_PAR );
db.execSQL("DROP TABLE IF EXISTS " +QUARTIER );
db.execSQL("DROP TABLE IF EXISTS " +RUE );
onCreate(db);
    }


    public List <String> trier_champ(String champ) {

    String champ_a_analyser = champ + " - @";
    List<String> resultat = new ArrayList<String>();
    int indice = 0;
    int indice_tb = 0;
    String qu = "";

    //ANLYSE DU CHAMP
    while (champ_a_analyser.charAt(indice) != '@') {
        qu = "";
        while (!" - ".equals(champ_a_analyser.substring(indice, indice + 1) + champ_a_analyser.substring(indice + 1, indice + 2) + champ_a_analyser.substring(indice + 2, indice + 3))) {
            qu += champ_a_analyser.charAt(indice);
            indice += 1;
        }
        resultat.add(qu);
        indice_tb += 1;
        indice += 3;
    }
return resultat;
}


//-----------------------------------------------------------------------------------------------------------------²
    //FONCTION POUR AFFICHER TOUT LES LIBELLE DE GBAKA
    public ArrayList<String> Chercher_Libellé(String table ,String libellé){
        ArrayList<String> lib=new ArrayList<>();
        String selectQuery = "SELECT "+  libellé+"   FROM " + table ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor.moveToNext()){
            System.out.println("libellé"+cursor.getString(0));
            lib.add(cursor.getString(0));
        }

        db.close();



        return lib;
    }

//------------------------------------------------ FIN ----------------------------------------------------------------------



    private String trier_champ_iti(String chemins){

String moy="";
        String [] value=new String[2];
        if (chemins.isEmpty()){

        }else{
            tmp_moy_tp_iti=chemins;
            int i=1;
            do{
                String tri="";
                chemins+="@";
                chemins= chemins.replace(");", ",;");
                int indice=0;


                while ( chemins.charAt(i)!=';'){
                    tri="";
                    while (chemins.charAt(i)!=','){
                        tri+=chemins.charAt(i);
                        i++;

                    }

                    if (indice==1){
                        value[indice]=tri.replace(")", "").replace("(", "");

                    }else{
                        value[indice]=tri;

                    }



                    System.out.println(value[indice]);
                    i++;
                    indice++;
                }
                    moy+=value[0]+"\n";

                //m.addRow(new Object[] {value[0],value[1]});



                i+=2;

            }while(chemins.charAt(i)!='@');

        }

        return moy;
    }




    public ArrayList<Moy_Tp_Iti> AfficherDetail_Iti(String chemins){


        ArrayList<Moy_Tp_Iti> tp=new ArrayList<>();
        int id_iti=1;


        String [] value=new String[2];
        if (chemins.isEmpty()){
            tmp_moy_tp_iti=chemins;
        }else{

            int i=1;
            do{
                String tri="";
                chemins+="@";
                chemins= chemins.replace(");", ",;");
                int indice=0;


                while ( chemins.charAt(i)!=';'){
                    tri="";
                    while (chemins.charAt(i)!=','){
                        tri+=chemins.charAt(i);
                        i++;

                    }

                    if (indice==1){
                        value[indice]=tri.replace(")", "").replace("(", "");

                    }else{
                        value[indice]=tri;

                    }



                    System.out.println(value[indice]);
                    i++;
                    indice++;
                }

                Moy_Tp_Iti tpi=new Moy_Tp_Iti();
                tpi.setID(id_iti);
                tpi.setLIB_TP_ITI(value[0]);
                tpi.setTYPE_TP_ITI(value[1]);

                tp.add(tpi);
                id_iti++;
                //m.addRow(new Object[] {value[0],value[1]});



                i+=2;

            }while(chemins.charAt(i)!='@');

        }

        return tp;
    }




    //afficher toutes les lignes
    public ArrayList <String> AllLieu() {
        System.out.println("pffffffffffffffffffffff");

        ArrayList<String> lieu_all = new ArrayList<>();

        // declaration de la requete
        String selectQuery = "SELECT  nom_lieu FROM lieu"  ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


            while (cursor.moveToNext()){
                System.out.println("lieu  LIEU"+cursor.getString(0));
                lieu_all.add(cursor.getString(0));
            }

        db.close();

        // return contact list
        return lieu_all;

    }






    //afficher toutes les lignes
    public Itineraire Afficher_Iti(String lib_iti) {
       Itineraire iti=new Itineraire();

        // declaration de la requete
        String selectQuery = "SELECT "+MOYEN_TP_ITI+" , "+TYPE_ITI+" , "+DETAIL_ITI+" FROM "+ITINERAIRE
                +" WHERE "+LIB_ITI+"=?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{lib_iti});


        while (cursor.moveToNext()){
            iti.setMOYEN_TP_ITI(trier_champ_iti(cursor.getString(0)));
            iti.setTYPE_ITI(cursor.getString(1));

            String detail_iti=cursor.getString(2);
            iti.setDETAIL_ITI(detail_iti.replace(".n",".\n"));
            //iti.setMOYEN_TP_ITI(cursor.getString(0));

        }

        db.close();
        return iti;

    }










    //afficher toutes les lignes
    public ArrayList <String> AllIti() {

        ArrayList<String> iti_all = new ArrayList<>();

        // declaration de la requete
        String selectQuery = "SELECT  lib_iti FROM itineraire"  ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        while (cursor.moveToNext()){
            iti_all.add(cursor.getString(0));
        }

        db.close();
        return iti_all;

    }





}