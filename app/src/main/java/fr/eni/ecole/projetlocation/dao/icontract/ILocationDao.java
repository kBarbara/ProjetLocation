package fr.eni.ecole.projetlocation.dao.icontract;

/**
 * Created by Administrateur on 20/10/2017.
 */
public class ILocationDao {
    public static final String TABLE_LOCATIONS = "locations";
    public static final String COLUMN_ID_LOCATIONS  = "id";
    public static final String COLUMN_ID_CLIENT_LOCATIONS  = "id_client";
    public static final String COLUMN_DATE_DEPART_LOCATIONS  = "depart";
    public static final String COLUMN_DATE_RETOUR_LOCATIONS  = "retour";
    public static final String COLUMN_ID_VEHICULE_LOCATIONS  = "id_vehicule";

    public static final String CREATE_TABLE_LOCATIONS= "create table "
            + TABLE_LOCATIONS + "(" + COLUMN_ID_LOCATIONS
            + " integer primary key autoincrement, " + COLUMN_ID_CLIENT_LOCATIONS
            + " integer not null, "+ COLUMN_DATE_DEPART_LOCATIONS
            + " text not null, "+ COLUMN_DATE_RETOUR_LOCATIONS
            + " text not null, "+ COLUMN_ID_VEHICULE_LOCATIONS
            + " integer not null"
            + ");";
}