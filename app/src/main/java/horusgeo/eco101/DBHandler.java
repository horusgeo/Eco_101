package horusgeo.eco101;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafael on 24/05/2016.
 */
public class DBHandler extends SQLiteOpenHelper {

    private static final int nTables = 12;

    private static final String DATABASE_NAME = "eco101.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_REGISTERS = "projeto";
    private static final String TABLE_PROP = "proprietario";
    private static final String TABLE_CONJ = "conjuge";
    private static final String TABLE_END_RES = "end_res";
    private static final String TABLE_END_OBJ = "end_obj";
    private static final String TABLE_ID_PROP = "id_prop";
    private static final String TABLE_BENF = "benfeitoria";
    private static final String TABLE_PLANT = "plantacao";
    private static final String TABLE_DESC = "relatorio";
    private static final String TABLE_DOCS = "documentos";
    private static final String TABLE_LATLNG = "latlng";
    private static final String TABLE_OBS = "observacao";
    private static final String TABLE_USER = "users";

    private static final String ID = "_id";

    private static final String USER = "user";

    private static final String NOME_PROJETO = "nome_projeto";
    private static final String ID_PROPRIETARIO = "id_proprietario";
    private static final String LOCAL_VISITA = "local_visita";
    private static final String DATA_VISITA = "data_visita";
    private static final String STATUS = "status";

    private static final String ID_PROP = "id_proprietario";
    private static final String NOME_PROPRIETARIO = "nome";
    private static final String NACIONALIDADE_PROPRIETARIO = "nacionalidade";
    private static final String PROFISSAO_PROPRIETARIO = "profissao";
    private static final String ESTADO_CIVIL = "estado_civil";
    private static final String DOC_ID_PROPRIETARIO = "num_id";
    private static final String DOC_ID_TIPO_PROPRIETARIO = "tipo_id";
    private static final String CPF_PROPRIETARIO = "cpf";
    private static final String TEL_PROPRIETARIO_1 = "tel_1";
    private static final String TEL_PROPRIETARIO_2 = "tel_2";
    private static final String EMAIL_PROPRIETARIO = "email";
    private static final String POSSPROP = "poss_prop";

    private static final String ID_CONJ = "id_proprietario";
    private static final String NOME_CONJ = "nome";
    private static final String NACIONALIDADE_CONJ = "nacionalidade";
    private static final String PROFISSAO_CONJ = "profissao";
    private static final String DOC_ID_CONJ = "num_id";
    private static final String DOC_ID_TIPO_CONJ = "tipo_id";
    private static final String CPF_CONJ = "cpf";
    private static final String TEL_CONJ_1 = "tel_1";
    private static final String TEL_CONJ_2 = "tel_2";

    private static final String ID_END_RES = "id_proprietario";
    private static final String RUA_END_RES = "rua";
    private static final String N_END_RES = "num";
    private static final String COMPL_END_RES = "compl";
    private static final String BAIRRO_END_RES = "bairro";
    private static final String CEP_END_RES = "cep";
    private static final String MUNICIPIO_END_RES = "municipio";
    private static final String UF_MUN_END_RES = "uf_1";
    private static final String COMARCA_END_RES = "comarca_";
    private static final String UF_COM_END_RES = "uf_2";
    private static final String P_REF_END_RES = "p_ref";

    private static final String ID_END_OBJ = "id_proprietario";
    private static final String RUA_END_OBJ = "rua";
    private static final String N_END_OBJ = "num";
    private static final String COMPL_END_OBJ = "compl";
    private static final String BAIRRO_END_OBJ = "bairro";
    private static final String CEP_END_OBJ = "cep";
    private static final String P_REF_END_OBJ = "p_ref";

    private static final String ID_ID_PROP = "id_proprietario";
    private static final String ZONEAMENTO = "zoneamento";
    private static final String TOPOGRAFIA = "topografia";
    private static final String INFRAREDEEL = "rede_eletrica";
    private static final String INFRASINTEL = "sinal_telefone";
    private static final String INFRAABASAGUA = "abastecimento_agua";
    private static final String MANANCIAL = "manancial";
    private static final String OBS_ID_PROP = "obs_id_prop";

    private static final String ID_BENF = "id_proprietario";
    private static final String TIPO_BENF = "tipo";
    private static final String IDADE_BENF = "idade";
    private static final String CONSERVACAO_BENF = "conservacao";
    private static final String ID_BENF_PHOTOS = "id_fotos";
    private static final String OBS_BENF = "obs_benf";

    private static final String ID_PLANT = "id_proprietario";
    private static final String TIPO_PLANT = "tipo";
    private static final String IDADE_PLANT = "idade";
    private static final String COMPLEMENTO_PLANT = "conservacao";
    private static final String ID_PLANT_PHOTOS = "id_fotos";
    private static final String OBS_PLANT = "obs_plant";

    private static final String ID_DESC = "id_proprietario";
    private static final String HORARIO_CHEGADA = "horario_chegada";
    private static final String HORARIO_SAIDA = "horario_saida";
    private static final String DESCRICAO_VISITA = "descricao_visita";
    private static final String RESPONSAVEL = "responsavel";

    private static final String PATH = "caminho";
    private static final String ID_DOC = "id_proprietario";
    private static final String TYPE = "tipo";
    private static final String NAME = "nome";

    private static final String LAT = "lat";
    private static final String LNG = "lng";
    private static final String TYPE_LATLNG = "tipo";
    private static final String TEXT_LATLNG = "texto_pin";
    private static final String ID_LATLNG = "id_proprietario";

    private static final String OBS = "obs";
    private static final String REGISTERSTATUS = "registerstatus";
    private static final String ID_OBS = "_id_obs";

    public DBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("SQL", "onCreate");
        String CREATE_REGISTER_TABLE = "CREATE TABLE " + TABLE_REGISTERS + "(" +
                ID + " INTEGER PRIMARY KEY," +
                NOME_PROJETO + " TEXT," +
                ID_PROPRIETARIO + " TEXT," +
                LOCAL_VISITA + " TEXT," +
                DATA_VISITA + " TEXT," +
                STATUS + " TEXT" +
                ")";
        db.execSQL(CREATE_REGISTER_TABLE);

        String CREATE_PROP_TABLE = "CREATE TABLE " + TABLE_PROP + "(" +
                ID + " INTEGER PRIMARY KEY," +
                NOME_PROPRIETARIO + " TEXT," +
                NACIONALIDADE_PROPRIETARIO + " TEXT," +
                PROFISSAO_PROPRIETARIO + " TEXT," +
                ESTADO_CIVIL + " TEXT," +
                DOC_ID_PROPRIETARIO + " TEXT," +
                DOC_ID_TIPO_PROPRIETARIO + " TEXT," +
                CPF_PROPRIETARIO + " TEXT," +
                TEL_PROPRIETARIO_1 + " TEXT," +
                TEL_PROPRIETARIO_2 + " TEXT," +
                EMAIL_PROPRIETARIO + " TEXT," +
                POSSPROP + " TEXT," +
                ID_PROP + " TEXT" +
                ")";
        db.execSQL(CREATE_PROP_TABLE);

        String CREATE_CONJ_TABLE = "CREATE TABLE " + TABLE_CONJ + "(" +
                ID + " INTEGER PRIMARY KEY," +
                NOME_CONJ + " TEXT," +
                NACIONALIDADE_CONJ + " TEXT," +
                PROFISSAO_CONJ + " TEXT," +
                DOC_ID_CONJ + " TEXT," +
                DOC_ID_TIPO_CONJ + " TEXT," +
                CPF_CONJ + " TEXT," +
                TEL_CONJ_1 + " TEXT," +
                TEL_CONJ_2 + " TEXT," +
                ID_CONJ + " TEXT" +
                ")";
        db.execSQL(CREATE_CONJ_TABLE);

        String CREATE_ENDRES_TABLE = "CREATE TABLE " + TABLE_END_RES + "(" +
                ID + " INTEGER PRIMARY KEY," +
                RUA_END_RES + " TEXT," +
                N_END_RES + " TEXT," +
                COMPL_END_RES + " TEXT," +
                BAIRRO_END_RES + " TEXT," +
                CEP_END_RES + " TEXT," +
                MUNICIPIO_END_RES + " TEXT," +
                UF_MUN_END_RES + " TEXT," +
                COMARCA_END_RES + " TEXT," +
                UF_COM_END_RES + " TEXT," +
                P_REF_END_RES + " TEXT," +
                ID_END_RES + " TEXT" +
                ")";
        db.execSQL(CREATE_ENDRES_TABLE);

        String CREATE_ENDOBJ_TABLE = "CREATE TABLE " + TABLE_END_OBJ + "(" +
                ID + " INTEGER PRIMARY KEY," +
                RUA_END_OBJ + " TEXT," +
                N_END_OBJ + " TEXT," +
                COMPL_END_OBJ + " TEXT," +
                BAIRRO_END_OBJ + " TEXT," +
                CEP_END_OBJ + " TEXT," +
                P_REF_END_OBJ + " TEXT," +
                ID_END_OBJ + " TEXT" +
                ")";
        db.execSQL(CREATE_ENDOBJ_TABLE);

        String CREATE_IDPROP_TABLE = "CREATE TABLE " + TABLE_ID_PROP + "(" +
                ID + " INTEGER PRIMARY KEY," +
                ZONEAMENTO + " TEXT," +
                TOPOGRAFIA + " TEXT," +
                INFRAREDEEL + " TEXT," +
                INFRASINTEL + " TEXT," +
                INFRAABASAGUA + " TEXT," +
                MANANCIAL + " TEXT," +
                OBS_ID_PROP + " TEXT," +
                ID_ID_PROP + " TEXT" +
                ")";
        db.execSQL(CREATE_IDPROP_TABLE);

        String CREATE_DESC_TABLE = "CREATE TABLE " + TABLE_DESC + "(" +
                ID + " INTEGER PRIMARY KEY," +
                HORARIO_CHEGADA + " TEXT," +
                HORARIO_SAIDA + " TEXT," +
                DESCRICAO_VISITA + " TEXT," +
                RESPONSAVEL + " TEXT," +
                ID_DESC + " TEXT" +
                ")";
        db.execSQL(CREATE_DESC_TABLE);

        String CREATE_DOCS_TABLE = "CREATE TABLE " + TABLE_DOCS + "(" +
                ID + " INTEGER PRIMARY KEY," +
                PATH + " TEXT," +
                TYPE + " TEXT," +
                NAME + " TEXT," +
                ID_DOC + " TEXT" +
                ")";
        db.execSQL(CREATE_DOCS_TABLE);

        String CREATE_BENF_TABLE = "CREATE TABLE " + TABLE_BENF + "(" +
                ID + " INTEGER PRIMARY KEY," +
                TIPO_BENF + " TEXT," +
                IDADE_BENF + " TEXT," +
                CONSERVACAO_BENF + " TEXT," +
                ID_BENF_PHOTOS + " TEXT," +
                ID_BENF + " TEXT" +
                ")";
        db.execSQL(CREATE_BENF_TABLE);

        String CREATE_PLANT_TABLE = "CREATE TABLE " + TABLE_PLANT + "(" +
                ID + " INTEGER PRIMARY KEY," +
                TIPO_PLANT + " TEXT," +
                IDADE_PLANT + " TEXT," +
                COMPLEMENTO_PLANT + " TEXT," +
                ID_PLANT_PHOTOS + " TEXT," +
                ID_PLANT + " TEXT" +
                ")";
        db.execSQL(CREATE_PLANT_TABLE);

        String CREATE_LATLNG_TABLE = "CREATE TABLE " + TABLE_LATLNG + "(" +
                ID + " INTEGER PRIMARY KEY," +
                LAT + " TEXT," +
                LNG + " TEXT," +
                TYPE_LATLNG + " TEXT," +
                TEXT_LATLNG + " TEXT," +
                ID_LATLNG + " TEXT" +
                ")";
        db.execSQL(CREATE_LATLNG_TABLE);

        String CREATE_OBS_TABLE = "CREATE TABLE " + TABLE_OBS + "(" +
                ID + " INTEGER PRIMARY KEY," +
                OBS + " TEXT," +
                REGISTERSTATUS + " TEXT," +
                ID_OBS + " TEXT" +
                ")";
        db.execSQL(CREATE_OBS_TABLE);

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(" +
                ID + " INTEGER PRIMARY KEY," +
                USER + " TEXT" +
                ")";
        db.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONJ);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_END_RES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_END_OBJ);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ID_PROP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DESC);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BENF);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLANT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LATLNG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    //Add Registers

    public void addRegister(Register cadastro) {

        ContentValues values = new ContentValues();

        values.put(NOME_PROJETO, cadastro.get_nome_projeto());
        values.put(ID_PROPRIETARIO, cadastro.get_id_prop());
        values.put(LOCAL_VISITA, cadastro.get_local_visita());
        values.put(DATA_VISITA, cadastro.get_data_visita());
        values.put(STATUS, cadastro.get_status());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_REGISTERS, null, values);
        db.close();
    }

    public void addProp(Register cadastro) {

        ContentValues values = new ContentValues();

        values.put(NOME_PROPRIETARIO, cadastro.get_nome_proprietario());
        values.put(NACIONALIDADE_PROPRIETARIO, cadastro.get_nacionalidade_prop());
        values.put(PROFISSAO_PROPRIETARIO, cadastro.get_profissao_prop());
        values.put(ESTADO_CIVIL, cadastro.get_estado_civil());
        values.put(DOC_ID_PROPRIETARIO, cadastro.get_doc_id_prop());
        values.put(DOC_ID_TIPO_PROPRIETARIO, cadastro.get_doc_id_tipo_prop());
        values.put(CPF_PROPRIETARIO, cadastro.get_cpf_prop());
        values.put(TEL_PROPRIETARIO_1, cadastro.get_tel_prop_1());
        values.put(TEL_PROPRIETARIO_2, cadastro.get_tel_prop_2());
        values.put(EMAIL_PROPRIETARIO, cadastro.get_email_prop());
        values.put(POSSPROP, cadastro.get_possProp());
        values.put(ID_PROP, cadastro.get_id_prop());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_PROP, null, values);
        db.close();
    }

    public void addConj(Register cadastro) {

        ContentValues values = new ContentValues();

        values.put(NOME_CONJ, cadastro.get_nome_conj());
        values.put(NACIONALIDADE_CONJ, cadastro.get_nacionalidade_conj());
        values.put(PROFISSAO_CONJ, cadastro.get_profissao_conj());
        values.put(DOC_ID_CONJ, cadastro.get_doc_id_conj());
        values.put(DOC_ID_TIPO_CONJ, cadastro.get_doc_id_tipo_conj());
        values.put(CPF_CONJ, cadastro.get_cpf_conj());
        values.put(TEL_CONJ_1, cadastro.get_tel_conj_1());
        values.put(TEL_CONJ_2, cadastro.get_tel_conj_2());
        values.put(ID_CONJ, cadastro.get_id_prop());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_CONJ, null, values);
        db.close();
    }

    public void addEndRes(Register cadastro) {

        ContentValues values = new ContentValues();

        values.put(RUA_END_RES, cadastro.get_rua_end_res());
        values.put(N_END_RES, cadastro.get_n_end_res());
        values.put(COMPL_END_RES, cadastro.get_compl_end_res());
        values.put(BAIRRO_END_RES, cadastro.get_bairro_end_res());
        values.put(CEP_END_RES, cadastro.get_cep_end_res());
        values.put(MUNICIPIO_END_RES, cadastro.get_municipio_end_res());
        values.put(UF_MUN_END_RES, cadastro.get_uf_mun_end_res());
        values.put(COMARCA_END_RES, cadastro.get_comarca_end_res());
        values.put(UF_COM_END_RES, cadastro.get_uf_com_end_res());
        values.put(P_REF_END_RES, cadastro.get_p_ref_end_res());
        values.put(ID_END_RES, cadastro.get_id_prop());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_END_RES, null, values);
        db.close();
    }

    public void addEndObj(Register cadastro) {

        ContentValues values = new ContentValues();

        values.put(RUA_END_OBJ, cadastro.get_rua_end_obj());
        values.put(N_END_OBJ, cadastro.get_n_end_obj());
        values.put(COMPL_END_OBJ, cadastro.get_compl_end_obj());
        values.put(BAIRRO_END_OBJ, cadastro.get_bairro_end_obj());
        values.put(CEP_END_OBJ, cadastro.get_cep_end_obj());
        values.put(P_REF_END_OBJ, cadastro.get_p_ref_end_obj());
        values.put(ID_END_OBJ, cadastro.get_id_prop());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_END_OBJ, null, values);
        db.close();
    }

    public void addIdProp(Register cadastro) {

        ContentValues values = new ContentValues();

        values.put(ZONEAMENTO, cadastro.get_zoneamento());
        values.put(TOPOGRAFIA, cadastro.get_topografia());
        values.put(INFRAREDEEL, cadastro.get_infraredeel());
        values.put(INFRASINTEL, cadastro.get_infrasintel());
        values.put(INFRAABASAGUA, cadastro.get_infraabasagua());
        values.put(MANANCIAL, cadastro.get_manacial());
        values.put(OBS_ID_PROP, cadastro.get_obs_id_prop());
        values.put(ID_ID_PROP, cadastro.get_id_prop());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_ID_PROP, null, values);
        db.close();
    }

    public void addDesc(Register cadastro) {

        ContentValues values = new ContentValues();

        values.put(HORARIO_CHEGADA, cadastro.get_horario_chegada());
        values.put(HORARIO_SAIDA, cadastro.get_horario_saida());
        values.put(DESCRICAO_VISITA, cadastro.get_descricao_visita());
        values.put(RESPONSAVEL, cadastro.get_responsavel());
        values.put(ID_DESC, cadastro.get_id_prop());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_DESC, null, values);
        db.close();
    }

    public void addDoc(ArrayList<Docs> docs){

        ContentValues values = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();

        for(Docs temp : docs){
            values.clear();
            values.put(PATH, temp.getPath());
            values.put(TYPE, temp.getType());
            values.put(NAME, temp.getName());
            values.put(ID_DOC, temp.getIdProp());

            db.insert(TABLE_DOCS, null, values);
        }
        db.close();

    }

    public void addBenf(ArrayList<Benfeitoria> benfs){

        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        for(Benfeitoria temp : benfs){
            values.clear();
            values.put(TIPO_BENF, temp.getTipo());
            values.put(IDADE_BENF, temp.getIdade());
            values.put(CONSERVACAO_BENF, temp.getConservacao());
            values.put(ID_BENF_PHOTOS, temp.getIdBenf());
            values.put(ID_BENF, temp.getIdProp());

            db.insert(TABLE_BENF, null, values);
        }
        db.close();
    }

    public void addPlant(ArrayList<Benfeitoria> plants){

        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        for(Benfeitoria temp : plants){
            values.clear();
            values.put(TIPO_PLANT, temp.getTipo());
            values.put(IDADE_PLANT, temp.getIdade());
            values.put(COMPLEMENTO_PLANT, temp.getConservacao());
            values.put(ID_PLANT_PHOTOS, temp.getIdBenf());
            values.put(ID_PLANT, temp.getIdProp());

            db.insert(TABLE_PLANT, null, values);
        }
        db.close();
    }

    public  void addLatLng(Register cadastro){

        ContentValues values = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();

        int tam = cadastro.getLat().size();

        for(int i = 0; i < tam; i++){
            values.put(LAT, String.valueOf(cadastro.getLat().get(i)));
            values.put(LNG, String.valueOf(cadastro.getLng().get(i)));
            values.put(TYPE_LATLNG, "0");
            values.put(TEXT_LATLNG, cadastro.get_nome_proprietario());
            values.put(ID_LATLNG, cadastro.get_id_prop());

            db.insert(TABLE_LATLNG, null, values);
        }

        db.close();
    }

    public void addPinToLatLngTable(Double lat, Double lng, String texto){

        ContentValues values = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();

        values.put(LAT, String.valueOf(lat));
        values.put(LNG, String.valueOf(lng));
        values.put(TYPE_LATLNG, "0");
        values.put(TEXT_LATLNG, texto);
        values.put(ID_LATLNG, "-1");

        db.insert(TABLE_LATLNG, null, values);

        db.close();
    }

    public void addObs(Register cadastro){

        ContentValues values = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();

        values.put(OBS, cadastro.getObservacao());
        values.put(REGISTERSTATUS, String.valueOf(cadastro.getRegisterStatus()));
        values.put(ID_OBS, cadastro.get_id_prop());

        db.insert(TABLE_OBS, null, values);

        db.close();
    }

    //Update Registers

    public void updateRegister(Register cadastro, ArrayList<Benfeitoria> benf, ArrayList<Benfeitoria> plants,  ArrayList<Docs> docs, String idBck){

        ContentValues values = new ContentValues();

        values.put(NOME_PROJETO, cadastro.get_nome_projeto());
        values.put(ID_PROPRIETARIO, cadastro.get_id_prop());
        values.put(LOCAL_VISITA, cadastro.get_local_visita());
        values.put(DATA_VISITA, cadastro.get_data_visita());
        values.put(STATUS, cadastro.get_status());

        SQLiteDatabase db = this.getWritableDatabase();

        db.update(TABLE_REGISTERS, values, ID + "=" + cadastro.get_id(), null);
        db.close();

        updateRegisterProp(cadastro, idBck);
        updateRegisterConj(cadastro, idBck);
        updateRegisterEndRes(cadastro, idBck);
        updateRegisterEndObj(cadastro, idBck);
        updateRegisterIdProp(cadastro, idBck);
        updateRegisterDesc(cadastro, idBck);
        updateRegisterObs(cadastro, idBck);
        updateRegisterPins(cadastro, idBck);
        updateRegisterBenf(cadastro, benf, idBck);
        updateRegisterPlant(cadastro, plants, idBck);
        updateRegisterDoc(cadastro, docs, idBck);
        updateRegisterObs(cadastro, idBck);

    }

    public void updateRegisterProp(Register cadastro, String idBck){

        ContentValues values = new ContentValues();

        values.put(NOME_PROPRIETARIO, cadastro.get_nome_proprietario());
        values.put(NACIONALIDADE_PROPRIETARIO, cadastro.get_nacionalidade_prop());
        values.put(PROFISSAO_PROPRIETARIO, cadastro.get_profissao_prop());
        values.put(ESTADO_CIVIL, cadastro.get_estado_civil());
        values.put(DOC_ID_PROPRIETARIO, cadastro.get_doc_id_prop());
        values.put(DOC_ID_TIPO_PROPRIETARIO, cadastro.get_doc_id_tipo_prop());
        values.put(CPF_PROPRIETARIO, cadastro.get_cpf_prop());
        values.put(TEL_PROPRIETARIO_1, cadastro.get_tel_prop_1());
        values.put(TEL_PROPRIETARIO_2, cadastro.get_tel_prop_2());
        values.put(EMAIL_PROPRIETARIO, cadastro.get_email_prop());
        values.put(POSSPROP, cadastro.get_possProp());
        values.put(ID_PROP, cadastro.get_id_prop());

        SQLiteDatabase db = this.getWritableDatabase();

        if(cadastro.get_id_prop().equals(idBck)){
            db.update(TABLE_PROP, values, ID_PROP + "=" + cadastro.get_id_prop(), null);
        }else{
            db.update(TABLE_PROP, values, ID_PROP + "=" + idBck, null);
        }

        db.close();
    }

    void updateRegisterConj(Register cadastro, String idBck){
        ContentValues values = new ContentValues();

        values.put(NOME_CONJ, cadastro.get_nome_conj());
        values.put(NACIONALIDADE_CONJ, cadastro.get_nacionalidade_conj());
        values.put(PROFISSAO_CONJ, cadastro.get_profissao_conj());
        values.put(DOC_ID_CONJ, cadastro.get_doc_id_conj());
        values.put(DOC_ID_TIPO_CONJ, cadastro.get_doc_id_tipo_conj());
        values.put(CPF_CONJ, cadastro.get_cpf_conj());
        values.put(TEL_CONJ_1, cadastro.get_tel_conj_1());
        values.put(TEL_CONJ_2, cadastro.get_tel_conj_2());
        values.put(ID_CONJ, cadastro.get_id_prop());

        SQLiteDatabase db = this.getWritableDatabase();

        if(cadastro.get_id_prop().equals(idBck)){
            db.update(TABLE_CONJ, values, ID_CONJ + "=" + cadastro.get_id_prop(), null);
        }else{
            db.update(TABLE_CONJ, values, ID_CONJ + "=" + idBck, null);
        }

        db.close();
    }

    public void updateRegisterEndRes(Register cadastro, String idBck){

        ContentValues values = new ContentValues();

        values.put(RUA_END_RES, cadastro.get_rua_end_res());
        values.put(N_END_RES, cadastro.get_n_end_res());
        values.put(COMPL_END_RES, cadastro.get_compl_end_res());
        values.put(BAIRRO_END_RES, cadastro.get_bairro_end_res());
        values.put(CEP_END_RES, cadastro.get_cep_end_res());
        values.put(MUNICIPIO_END_RES, cadastro.get_municipio_end_res());
        values.put(UF_MUN_END_RES, cadastro.get_uf_mun_end_res());
        values.put(COMARCA_END_RES, cadastro.get_comarca_end_res());
        values.put(UF_COM_END_RES, cadastro.get_uf_com_end_res());
        values.put(P_REF_END_RES, cadastro.get_p_ref_end_res());
        values.put(ID_END_RES, cadastro.get_id_prop());

        SQLiteDatabase db = this.getWritableDatabase();

        if(cadastro.get_id_prop().equals(idBck)){
            db.update(TABLE_END_RES, values, ID_END_RES + "=" + cadastro.get_id_prop(), null);
        }else{
            db.update(TABLE_END_RES, values, ID_END_RES + "=" + idBck, null);
        }

        db.close();

    }

    public void updateRegisterEndObj(Register cadastro, String idBck){

        ContentValues values = new ContentValues();

        values.put(RUA_END_OBJ, cadastro.get_rua_end_obj());
        values.put(N_END_OBJ, cadastro.get_n_end_obj());
        values.put(COMPL_END_OBJ, cadastro.get_compl_end_obj());
        values.put(BAIRRO_END_OBJ, cadastro.get_bairro_end_obj());
        values.put(CEP_END_OBJ, cadastro.get_cep_end_obj());
        values.put(P_REF_END_OBJ, cadastro.get_p_ref_end_obj());
        values.put(ID_END_OBJ, cadastro.get_id_prop());

        SQLiteDatabase db = this.getWritableDatabase();

        if(cadastro.get_id_prop().equals(idBck)){
            db.update(TABLE_END_OBJ, values, ID_END_OBJ + "=" + cadastro.get_id_prop(), null);
        }else{
            db.update(TABLE_END_OBJ, values, ID_END_OBJ + "=" + idBck, null);
        }

        db.close();

    }

    public void updateRegisterIdProp(Register cadastro, String idBck){

        ContentValues values = new ContentValues();

        values.put(ZONEAMENTO, cadastro.get_zoneamento());
        values.put(TOPOGRAFIA, cadastro.get_topografia());
        values.put(INFRAREDEEL, cadastro.get_infraredeel());
        values.put(INFRASINTEL, cadastro.get_infrasintel());
        values.put(INFRAABASAGUA, cadastro.get_infraabasagua());
        values.put(MANANCIAL, cadastro.get_manacial());
        values.put(OBS_ID_PROP, cadastro.get_obs_id_prop());
        values.put(ID_ID_PROP, cadastro.get_id_prop());

        SQLiteDatabase db = this.getWritableDatabase();

        if(cadastro.get_id_prop().equals(idBck)){
            db.update(TABLE_ID_PROP, values, ID_ID_PROP + "=" + cadastro.get_id_prop(), null);
        }else{
            db.update(TABLE_ID_PROP, values, ID_ID_PROP + "=" + idBck, null);
        }

        db.close();

    }

    public void updateRegisterDesc(Register cadastro, String idBck) {

        ContentValues values = new ContentValues();

        values.put(HORARIO_CHEGADA, cadastro.get_horario_chegada());
        values.put(HORARIO_SAIDA, cadastro.get_horario_saida());
        values.put(DESCRICAO_VISITA, cadastro.get_descricao_visita());
        values.put(RESPONSAVEL, cadastro.get_responsavel());
        values.put(ID_DESC, cadastro.get_id_prop());

        SQLiteDatabase db = this.getWritableDatabase();

        if (cadastro.get_id_prop().equals(idBck)) {
            db.update(TABLE_DESC, values, ID_DESC + "=" + cadastro.get_id_prop(), null);
        } else {
            db.update(TABLE_DESC, values, ID_DESC + "=" + idBck, null);
        }

        db.close();
    }

    public void updateRegisterObs(Register cadastro, String idBck){

        ContentValues values = new ContentValues();

        values.put(OBS, cadastro.getObservacao());
        values.put(REGISTERSTATUS, String.valueOf(cadastro.getRegisterStatus()));
        values.put(ID_OBS, cadastro.get_id_prop());

        SQLiteDatabase db = this.getWritableDatabase();

        if (cadastro.get_id_prop().equals(idBck)) {
            db.update(TABLE_OBS, values, ID_OBS + "=" + cadastro.get_id_prop(), null);
        } else {
            db.update(TABLE_OBS, values, ID_OBS + "=" + idBck, null);
        }

        db.close();
    }

    public void updateRegisterPins(Register cadastro, String idBck){

        ArrayList<Double> lat;
        ArrayList<Double> lng;

        if(cadastro.get_id_prop().equals(idBck)) {
            lat = getLats(cadastro.get_id_prop());
            lng = getLngs(cadastro.get_id_prop());
            removePins(cadastro.get_id_prop());
        } else {
            lat = getLats(idBck);
            lng = getLngs(idBck);
            removePins(idBck);
        }
        ContentValues values = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();

        int tam = lat.size();

        for(int i = 0; i < tam; i++){
            values.put(LAT, String.valueOf(lat.get(i)));
            values.put(LNG, String.valueOf(lng.get(i)));
            values.put(TYPE_LATLNG, cadastro.getTipoLatLng());
            values.put(TEXT_LATLNG, cadastro.get_nome_proprietario());
            values.put(ID_LATLNG, cadastro.get_id_prop());
            db.insert(TABLE_LATLNG, null, values);

        }

        db.close();

    }

    public void updateRegisterDoc(Register cadastro, ArrayList<Docs> docs, String idBck){

        if(cadastro.get_id_prop().equals(idBck))
            removeDocs(cadastro.get_id_prop());
        else
            removeDocs(idBck);

        ContentValues values = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();

        for(Docs temp : docs){
            values.put(PATH, temp.getPath());
            values.put(TYPE, temp.getType());
            values.put(NAME, temp.getName());
            values.put(ID_DOC, cadastro.get_id_prop());
            db.insert(TABLE_DOCS, null, values);

        }

        db.close();

    }

    public void updateRegisterDoc(ArrayList<Docs> docs, String idBck){

        removeDocs(idBck);

        ContentValues values = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();

        for(Docs temp : docs){
            values.put(PATH, temp.getPath());
            values.put(TYPE, temp.getType());
            values.put(NAME, temp.getName());
            values.put(ID_DOC, idBck);
            db.insert(TABLE_DOCS, null, values);

        }

        db.close();

    }

    public void updateRegisterBenf(Register cadastro, ArrayList<Benfeitoria> benfs, String idBck){

        if(cadastro.get_id_prop().equals(idBck))
            removeBenfeitorias(cadastro.get_id_prop());
        else
            removeBenfeitorias(idBck);

        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        for(Benfeitoria temp : benfs){
            values.clear();
            values.put(TIPO_BENF, temp.getTipo());
            values.put(IDADE_BENF, temp.getIdade());
            values.put(CONSERVACAO_BENF, temp.getConservacao());
            values.put(ID_BENF_PHOTOS, temp.getIdBenf());
            values.put(ID_BENF, cadastro.get_id_prop());
            db.insert(TABLE_BENF, null, values);

        }
        db.close();

    }

    public void updateRegisterBenf(ArrayList<Benfeitoria> benfs, String idProp){
        removeBenfeitorias(idProp);

        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        for(Benfeitoria temp : benfs){
            values.clear();
            values.put(TIPO_BENF, temp.getTipo());
            values.put(IDADE_BENF, temp.getIdade());
            values.put(CONSERVACAO_BENF, temp.getConservacao());
            values.put(ID_BENF_PHOTOS, temp.getIdBenf());
            values.put(ID_BENF, idProp);
            db.insert(TABLE_BENF, null, values);

        }
        db.close();

    }

    public void updateRegisterPlants(ArrayList<Benfeitoria> plants, String idProp){
        removePlant(idProp);

        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        for(Benfeitoria temp : plants){
            values.clear();
            values.put(TIPO_PLANT, temp.getTipo());
            values.put(IDADE_PLANT, temp.getIdade());
            values.put(COMPLEMENTO_PLANT, temp.getConservacao());
            values.put(ID_PLANT_PHOTOS, temp.getIdBenf());
            values.put(ID_PLANT, idProp);
            db.insert(TABLE_PLANT, null, values);

        }
        db.close();

    }



    public void updateRegisterPlant(Register cadastro, ArrayList<Benfeitoria> plants, String idBck){

        if(cadastro.get_id_prop().equals(idBck))
            removePlant(cadastro.get_id_prop());
        else
            removePlant(idBck);

        ContentValues values = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        for(Benfeitoria temp : plants){
            values.clear();
            values.put(TIPO_PLANT, temp.getTipo());
            values.put(IDADE_PLANT, temp.getIdade());
            values.put(COMPLEMENTO_PLANT, temp.getConservacao());
            values.put(ID_PLANT_PHOTOS, temp.getIdBenf());
            values.put(ID_PLANT, cadastro.get_id_prop());
            db.insert(TABLE_PLANT, null, values);

        }
        db.close();

    }


    //Get Registers

    public List<Register> getAllIdNameRegisters(){
        List<Register> registerList = new ArrayList<Register>();
        String selectQuery = "SELECT * FROM " + TABLE_REGISTERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Register register = new Register();
                    register.set_id_prop(cursor.getString(2));

                    String query = "SELECT " + NOME_PROPRIETARIO + " FROM " + TABLE_PROP + " WHERE " + register.get_id_prop() + " = " + ID_PROP;

                    Cursor cursorProp = db.rawQuery(query, null);
                    try {
                        cursorProp.moveToFirst();
                        register.set_nome_proprietario(cursorProp.getString(0));
                    }finally{
                        cursorProp.close();
                    }

                    query = "SELECT " + REGISTERSTATUS + " FROM " + TABLE_OBS + " WHERE " + register.get_id_prop() + " = " + ID_OBS;
                    cursorProp = db.rawQuery(query, null);
                    try {
                        cursorProp.moveToFirst();
                        register.setRegisterStatus(Integer.parseInt(cursorProp.getString(0)));
                    }finally{
                        cursorProp.close();
                    }

                    registerList.add(register);
                } while (cursor.moveToNext());
            }
        }finally{
                cursor.close();
        }
        db.close();
        return registerList;
    }

    public Register getRegister(String idProp) {
        Register register = new Register();

        String query = "SELECT * FROM " + TABLE_REGISTERS + " WHERE " + ID_PROPRIETARIO + " = " + idProp;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                register.set_id(Integer.parseInt(cursor.getString(0)));
                register.set_nome_projeto(cursor.getString(1));
                register.set_id_prop(cursor.getString(2));
                register.set_local_visita(cursor.getString(3));
                register.set_data_visita(cursor.getString(4));
                register.set_status(cursor.getString(5));
            }
        }finally{
                cursor.close();
        }

        query = "SELECT * FROM " + TABLE_PROP + " WHERE " + ID_PROP + " = " + idProp;

        cursor = db.rawQuery(query, null);
        try{
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                register.set_nome_proprietario(cursor.getString(1));
                register.set_nacionalidade_prop(cursor.getString(2));
                register.set_profissao_prop(cursor.getString(3));
                register.set_estado_civil(cursor.getString(4));
                register.set_doc_id_prop(cursor.getString(5));
                register.set_doc_id_tipo_prop(cursor.getString(6));
                register.set_cpf_prop(cursor.getString(7));
                register.set_tel_prop_1(cursor.getString(8));
                register.set_tel_prop_2(cursor.getString(9));
                register.set_email_prop(cursor.getString(10));
                register.set_possProp(cursor.getString(11));
            }
        }finally{
                cursor.close();
        }

        query = "SELECT * FROM " + TABLE_CONJ + " WHERE " + ID_CONJ + " = " + idProp;

        cursor = db.rawQuery(query, null);
        try{
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                register.set_nome_conj(cursor.getString(1));
                register.set_nacionalidade_conj(cursor.getString(2));
                register.set_profissao_conj(cursor.getString(3));
                register.set_doc_id_conj(cursor.getString(4));
                register.set_doc_id_tipo_conj(cursor.getString(5));
                register.set_cpf_conj(cursor.getString(6));
                register.set_tel_conj_1(cursor.getString(7));
                register.set_tel_conj_2(cursor.getString(8));
            }
        }finally{
            if(cursor != null)
                cursor.close();
        }

        query = "SELECT * FROM " + TABLE_END_RES + " WHERE " + ID_END_RES + " = " + idProp;

        cursor = db.rawQuery(query, null);

        try{
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                register.set_rua_end_res(cursor.getString(1));
                register.set_n_end_res(cursor.getString(2));
                register.set_compl_end_res(cursor.getString(3));
                register.set_bairro_end_res(cursor.getString(4));
                register.set_cep_end_res(cursor.getString(5));
                register.set_municipio_end_res(cursor.getString(6));
                register.set_uf_mun_end_res(cursor.getString(7));
                register.set_comarca_end_res(cursor.getString(8));
                register.set_uf_com_end_res(cursor.getString(9));
                register.set_p_ref_end_res(cursor.getString(10));
            }
        }finally{
                cursor.close();
        }

        query = "SELECT * FROM " + TABLE_END_OBJ + " WHERE " + ID_END_OBJ + " = " + idProp;

        cursor = db.rawQuery(query, null);

        try{
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                register.set_rua_end_obj(cursor.getString(1));
                register.set_n_end_obj(cursor.getString(2));
                register.set_compl_end_obj(cursor.getString(3));
                register.set_bairro_end_obj(cursor.getString(4));
                register.set_cep_end_obj(cursor.getString(5));
                register.set_p_ref_end_obj(cursor.getString(6));
            }
        }finally{
                cursor.close();
        }

        query = "SELECT * FROM " + TABLE_ID_PROP + " WHERE " + ID_ID_PROP + " = " + idProp;

        cursor = db.rawQuery(query, null);

        try{
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                register.set_zoneamento(cursor.getString(1));
                register.set_topografia(cursor.getString(2));
                register.set_infraredeel(cursor.getString(3));
                register.set_infrasintel(cursor.getString(4));
                register.set_infraabasagua(cursor.getString(5));
                register.set_manacial(cursor.getString(6));
                register.set_obs_id_prop(cursor.getString(7));
            }
        }finally{
                cursor.close();
        }

        query = "SELECT * FROM " + TABLE_DESC + " WHERE " + ID_DESC + " = " + idProp;

        cursor = db.rawQuery(query, null);

        try {
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                register.set_horario_chegada(cursor.getString(1));
                register.set_horario_saida(cursor.getString(2));
                register.set_descricao_visita(cursor.getString(3));
                register.set_responsavel(cursor.getString(4));
            }
        }finally{
                cursor.close();
        }

        query = "SELECT * FROM " + TABLE_LATLNG + " WHERE " + ID_LATLNG + " = " + idProp;

        cursor = db.rawQuery(query, null);
        ArrayList<Double> lat = new ArrayList<Double>();
        ArrayList<Double> lng = new ArrayList<Double>();

        try{
            if (cursor.moveToFirst()) {
                int count = 0;
                do {
                    lat.add(Double.parseDouble(cursor.getString(1)));
                    lng.add(Double.valueOf(cursor.getString(2)));
                    register.setTipoLatLng(cursor.getString(3));
                    count++;
                } while (cursor.moveToNext());
            }
        }finally{
                cursor.close();
        }

        register.setLat(lat);
        register.setLng(lng);

        query = "SELECT * FROM " + TABLE_OBS + " WHERE " + ID_OBS + " = " + idProp;

        cursor = db.rawQuery(query, null);
        try{
            if (cursor.moveToFirst()) {
                cursor.moveToFirst();
                register.setObservacao(cursor.getString(1));
                register.setRegisterStatus(Integer.parseInt(cursor.getString(2)));
            }
        }finally{
                cursor.close();
        }
        db.close();

        return register;
    }

    public ArrayList<Benfeitoria> getBenfeitoria(String idProp) {

        ArrayList<Benfeitoria> list = new ArrayList<Benfeitoria>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_BENF + " WHERE " + ID_BENF + " = " + idProp;

        Cursor cursor = db.rawQuery(query, null);
        try{
            if (cursor.moveToFirst()) {
                do {
                    Benfeitoria benf = new Benfeitoria();
                    benf.setTipo(cursor.getString(1));
                    benf.setIdade(cursor.getString(2));
                    benf.setConservacao(cursor.getString(3));
                    benf.setIdBenf(cursor.getString(4));
                    benf.setIdProp(cursor.getString(5));
                    list.add(benf);
                } while (cursor.moveToNext());
            }
        }finally{
                cursor.close();
        }
        db.close();
        return list;
    }

    public ArrayList<Benfeitoria> getPlants(String idProp) {

        ArrayList<Benfeitoria> list = new ArrayList<Benfeitoria>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_PLANT + " WHERE " + ID_PLANT + " = " + idProp;

        Cursor cursor = db.rawQuery(query, null);
        try{
            if (cursor.moveToFirst()) {
                do {
                    Benfeitoria plant = new Benfeitoria();
                    plant.setTipo(cursor.getString(1));
                    plant.setIdade(cursor.getString(2));
                    plant.setConservacao(cursor.getString(3));
                    plant.setIdBenf(cursor.getString(4));
                    plant.setIdProp(cursor.getString(5));
                    list.add(plant);
                } while (cursor.moveToNext());
            }
        }finally{
                cursor.close();
        }
        db.close();
        return list;
    }

    public ArrayList<Docs> getDocs(String idProp){

        ArrayList<Docs> list = new ArrayList<Docs>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_DOCS + " WHERE " + ID_DOC + " = " + idProp;

        Cursor cursor = db.rawQuery(query, null);
        try{
            if(cursor.moveToFirst()){
                do{
                    Docs docs = new Docs();
                    docs.setPath(cursor.getString(1));
                    docs.setType(cursor.getString(2));
                    docs.setName(cursor.getString(3));
                    docs.setIdProp(cursor.getString(4));
                    list.add(docs);
                }while(cursor.moveToNext());
            }
        }finally{
                cursor.close();
        }
        db.close();
        return list;

    }

    //Delete Registers

    public void deleteRegister(String idProp) {

        boolean result = false;

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_REGISTERS, ID_PROPRIETARIO + " = " + idProp, null);
        db.delete(TABLE_PROP, ID_PROP + " = " + idProp, null);
        db.delete(TABLE_CONJ, ID_CONJ + " = " + idProp, null);
        db.delete(TABLE_END_RES, ID_END_RES + " = " + idProp, null);
        db.delete(TABLE_END_OBJ, ID_END_OBJ + " = " + idProp, null);
        db.delete(TABLE_ID_PROP, ID_ID_PROP + " = " + idProp, null);
        db.delete(TABLE_DESC, ID_DESC + " = " + idProp, null);
        db.delete(TABLE_DOCS, ID_DOC + " = " + idProp, null);
        db.delete(TABLE_BENF, ID_BENF + " = " + idProp, null);
        db.delete(TABLE_PLANT, ID_PLANT + " = " + idProp, null);
        db.delete(TABLE_LATLNG, ID_LATLNG + " = " + idProp, null);
        db.delete(TABLE_OBS, ID_OBS + " = " + idProp, null);
        db.close();

    }


    //Useful Functions

    public boolean isRegisterEmpty(){
        String countQuery = "SELECT  * FROM " + TABLE_REGISTERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = -1;
        try {
            count = cursor.getCount();
        }finally {
            cursor.close();
        }
        db.close();
        if(count == 0)
            return true;
        else
            return false;
    }

    public int getRegistersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_REGISTERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = -1;
        try {
            count = cursor.getCount();
        }finally {
            cursor.close();
        }

        // return count
        db.close();
        return count;
    }

    public int getBenfCount() {
        String countQuery = "SELECT  * FROM " + TABLE_BENF;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = -1;
        try {
            count = cursor.getCount();
        }finally {
            cursor.close();
        }

        // return count
        db.close();
        return count;
    }

    public int getNTables(){
        return nTables;
    }

    public String getTableNames(int n){
        switch(n){
            case 0:
                return TABLE_REGISTERS;
            case 1:
                return TABLE_PROP;
            case 2:
                return TABLE_CONJ;
            case 3:
                return TABLE_END_RES;
            case 4:
                return TABLE_END_OBJ;
            case 5:
                return TABLE_ID_PROP;
            case 6:
                return TABLE_DESC;
            case 7:
                return TABLE_DOCS;
        }
        return "";
    }

//    Generate CSV Files

    public File generateCSV(int n){
        File file = null;
        switch(n){
            case 0:
                file = tableRegisterToCSV();
                break;
            case 1:
                file = tablePropToCSV();
                break;
            case 2:
                file = tableConjToCSV();
                break;
            case 3:
                file = tableEndResToCSV();
                break;
            case 4:
                file = tableEndObjToCSV();
                break;
            case 5:
                file = tableIdPropToCSV();
                break;
            case 6:
                file = tableDescToCSV();
                break;
            case 7:
                file = tableDocToCSV();
                break;
            case 8:
                file = tableBenfToCSV();
                break;
            case 9:
                file = tablePlantToCSV();
                break;
            case 10:
                file = tableLatLngToCSV();
                break;
            case 11:
                file = tableObsToCSV();
                break;
        }
        return file;
    }

    public File tableRegisterToCSV() {
        File file = new File(Environment.getExternalStorageDirectory(), TABLE_REGISTERS + ".csv");

        String query = "SELECT * FROM " + TABLE_REGISTERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        CSVWriter csvWrite;
        try {
            //file.mkdirs();
            file.createNewFile();
            csvWrite = new CSVWriter(new FileWriter(file), ';', CSVWriter.NO_QUOTE_CHARACTER);
//            csvWrite.writeNext(cursor.getColumnNames());
            if (cursor.moveToFirst()) {
                do {
                    //Which column you want to export
                    String arrStr[] = {cursor.getString(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5)};
                    csvWrite.writeNext(arrStr);
                } while (cursor.moveToNext());
            }
            csvWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            cursor.close();
            db.close();
        }
        return file;
    }

    public File tablePropToCSV(){
        File file = new File(Environment.getExternalStorageDirectory(), TABLE_PROP + ".csv");

        String query = "SELECT * FROM " + TABLE_PROP;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        CSVWriter csvWrite;
        try {
//            file.mkdirs();
            file.createNewFile();
            csvWrite = new CSVWriter(new FileWriter(file), ';', CSVWriter.NO_QUOTE_CHARACTER);
//            csvWrite.writeNext(cursor.getColumnNames());
            if (cursor.moveToFirst()) {
                do {
                    //Which column you want to export
                    String arrStr[] = {cursor.getString(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5),
                            cursor.getString(6), cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11),
                            cursor.getString(12)};
                    csvWrite.writeNext(arrStr);
                } while (cursor.moveToNext());
            }
            csvWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            cursor.close();
            db.close();
        }
        return file;
    }

    public File tableConjToCSV(){
        File file = new File(Environment.getExternalStorageDirectory(), TABLE_CONJ + ".csv");

        String query = "SELECT * FROM " + TABLE_CONJ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        CSVWriter csvWrite;
        try {
//            file.mkdirs();
            file.createNewFile();
            csvWrite = new CSVWriter(new FileWriter(file), ';', CSVWriter.NO_QUOTE_CHARACTER);
//            csvWrite.writeNext(cursor.getColumnNames());
            if (cursor.moveToFirst()) {
                do {
                    //Which column you want to export
                    String arrStr[] = {cursor.getString(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5),
                            cursor.getString(6), cursor.getString(7), cursor.getString(8),
                            cursor.getString(9)};
                    csvWrite.writeNext(arrStr);
                } while (cursor.moveToNext());
            }
            csvWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            cursor.close();
            db.close();
        }
        return file;
    }

    public File tableEndResToCSV(){
        File file = new File(Environment.getExternalStorageDirectory(), TABLE_END_RES + ".csv");

        String query = "SELECT * FROM " + TABLE_END_RES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        CSVWriter csvWrite;
        try {
//            file.mkdirs();
            file.createNewFile();
            csvWrite = new CSVWriter(new FileWriter(file), ';', CSVWriter.NO_QUOTE_CHARACTER);
//            csvWrite.writeNext(cursor.getColumnNames());
            if (cursor.moveToFirst()) {
                do {
                    //Which column you want to export
                    String arrStr[] = {cursor.getString(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5),
                            cursor.getString(6), cursor.getString(7), cursor.getString(8),
                            cursor.getString(9), cursor.getString(10), cursor.getString(11)};
                    csvWrite.writeNext(arrStr);
                } while (cursor.moveToNext());
            }
            csvWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            cursor.close();
            db.close();
        }
        return file;
    }

    public File tableEndObjToCSV(){
        File file = new File(Environment.getExternalStorageDirectory(), TABLE_END_OBJ + ".csv");

        String query = "SELECT * FROM " + TABLE_END_OBJ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        CSVWriter csvWrite;
        try {
//            file.mkdirs();
            file.createNewFile();
            csvWrite = new CSVWriter(new FileWriter(file), ';', CSVWriter.NO_QUOTE_CHARACTER);
//            csvWrite.writeNext(cursor.getColumnNames());
            if (cursor.moveToFirst()) {
                do {
                    //Which column you want to export
                    String arrStr[] = {cursor.getString(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5),
                            cursor.getString(6), cursor.getString(7)};
                    csvWrite.writeNext(arrStr);
                } while (cursor.moveToNext());
            }
            csvWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            cursor.close();
            db.close();
        }
        return file;
    }

    public File tableIdPropToCSV(){
        File file = new File(Environment.getExternalStorageDirectory(), TABLE_ID_PROP + ".csv");

        String query = "SELECT * FROM " + TABLE_ID_PROP;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        CSVWriter csvWrite;
        try {
//            file.mkdirs();
            file.createNewFile();
            csvWrite = new CSVWriter(new FileWriter(file), ';', CSVWriter.NO_QUOTE_CHARACTER);
//            csvWrite.writeNext(cursor.getColumnNames());
            if (cursor.moveToFirst()) {
                do {
                    //Which column you want to export
                    String arrStr[] = {cursor.getString(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5),
                            cursor.getString(6), cursor.getString(7), cursor.getString(8)};
                    csvWrite.writeNext(arrStr);
                } while (cursor.moveToNext());
            }
            csvWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            cursor.close();
            db.close();
        }
        return file;
    }

    public File tableDescToCSV(){
        File file = new File(Environment.getExternalStorageDirectory(), TABLE_DESC + ".csv");

        String query = "SELECT * FROM " + TABLE_DESC;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        CSVWriter csvWrite;
        try {
//            file.mkdirs();
            file.createNewFile();
            csvWrite = new CSVWriter(new FileWriter(file), ';', CSVWriter.NO_QUOTE_CHARACTER);
//            csvWrite.writeNext(cursor.getColumnNames());
            if (cursor.moveToFirst()) {
                do {
                    //Which column you want to export
                    String arrStr[] = {cursor.getString(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5)};
                    csvWrite.writeNext(arrStr);
                } while (cursor.moveToNext());
            }
            csvWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            cursor.close();
            db.close();
        }
        return file;
    }

    public File tableDocToCSV(){
        File file = new File(Environment.getExternalStorageDirectory(), TABLE_DOCS + ".csv");

        String query = "SELECT * FROM " + TABLE_DOCS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        CSVWriter csvWrite;
        try {
//            file.mkdirs();
            file.createNewFile();
            csvWrite = new CSVWriter(new FileWriter(file), ';', CSVWriter.NO_QUOTE_CHARACTER);
//            csvWrite.writeNext(cursor.getColumnNames());
            if (cursor.moveToFirst()){
                do {
                    //Which column you want to export
                    String arrStr[] = {cursor.getString(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4)};
                    csvWrite.writeNext(arrStr);
                } while (cursor.moveToNext());
            }
            csvWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            cursor.close();
            db.close();
        }
        return file;
    }

    public File tableBenfToCSV(){
        File file = new File(Environment.getExternalStorageDirectory(), TABLE_BENF + ".csv");

        String query = "SELECT * FROM " + TABLE_BENF;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        CSVWriter csvWrite;
        try {
//            file.mkdirs();
            file.createNewFile();
            csvWrite = new CSVWriter(new FileWriter(file), ';', CSVWriter.NO_QUOTE_CHARACTER);
//            csvWrite.writeNext(cursor.getColumnNames());
            if (cursor.moveToFirst()){
                do {
                    //Which column you want to export
                    String arrStr[] = {cursor.getString(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5)};
                    csvWrite.writeNext(arrStr);
                } while (cursor.moveToNext());
            }
            csvWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            cursor.close();
            db.close();
        }
        return file;
    }

    public File tablePlantToCSV(){
        File file = new File(Environment.getExternalStorageDirectory(), TABLE_PLANT + ".csv");

        String query = "SELECT * FROM " + TABLE_PLANT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        CSVWriter csvWrite;
        try {
//            file.mkdirs();
            file.createNewFile();
            csvWrite = new CSVWriter(new FileWriter(file), ';', CSVWriter.NO_QUOTE_CHARACTER);
//            csvWrite.writeNext(cursor.getColumnNames());
            if (cursor.moveToFirst()){
                do {
                    //Which column you want to export
                    String arrStr[] = {cursor.getString(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5)};
                    csvWrite.writeNext(arrStr);
                } while (cursor.moveToNext());
            }
            csvWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            cursor.close();
            db.close();
        }
        return file;
    }

    public File tableLatLngToCSV(){
        File file = new File(Environment.getExternalStorageDirectory(), TABLE_LATLNG + ".csv");

        String query = "SELECT * FROM " + TABLE_LATLNG;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        CSVWriter csvWrite;
        try {
//            file.mkdirs();
            file.createNewFile();
            csvWrite = new CSVWriter(new FileWriter(file), ';', CSVWriter.NO_QUOTE_CHARACTER);
//            csvWrite.writeNext(cursor.getColumnNames());
            if (cursor.moveToFirst()){
                do {
                    //Which column you want to export
                    String arrStr[] = {cursor.getString(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3), cursor.getString(4), cursor.getString(5)};
                    csvWrite.writeNext(arrStr);
                } while (cursor.moveToNext());
            }
            csvWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            cursor.close();
            db.close();
        }
        return file;
    }

    public File tableObsToCSV(){
        File file = new File(Environment.getExternalStorageDirectory(), TABLE_OBS + ".csv");

        String query = "SELECT * FROM " + TABLE_OBS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        CSVWriter csvWrite;
        try {
//            file.mkdirs();
            file.createNewFile();
            csvWrite = new CSVWriter(new FileWriter(file), ';', CSVWriter.NO_QUOTE_CHARACTER);
//            csvWrite.writeNext(cursor.getColumnNames());
            if (cursor.moveToFirst()){
                do {
                    //Which column you want to export
                    String arrStr[] = {cursor.getString(0), cursor.getString(1), cursor.getString(2),
                            cursor.getString(3)};
                    csvWrite.writeNext(arrStr);
                } while (cursor.moveToNext());
            }
            csvWrite.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            cursor.close();
            db.close();
        }
        return file;
    }

    public ArrayList<String> getAllDocs(){
        ArrayList<String> list = new ArrayList<String>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_DOCS;

        Cursor cursor = db.rawQuery(query, null);
        try{
            if(cursor.moveToFirst()){
                do{
                    list.add(cursor.getString(1));
                }while(cursor.moveToNext());
            }
        }finally{
                cursor.close();
        }
        db.close();
        return list;

    }

    public ArrayList<Docs> getDBDocs(String idProp) {
        ArrayList<Docs> list = new ArrayList<Docs>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_DOCS + " WHERE " + ID_DOC + " = " + idProp;

        Cursor cursor = db.rawQuery(query, null);
        try{
            if (cursor.moveToFirst()) {
                do {
                    Docs doc = new Docs();
                    doc.setPath(cursor.getString(1));
                    doc.setType(cursor.getString(2));
                    doc.setName(cursor.getString(3));

                    list.add(doc);
                } while (cursor.moveToNext());
            }
        }finally{
                cursor.close();
        }
        db.close();
        return list;
    }


    public Boolean checkIfPropExists(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        int flag = 0;
        if(id!=null) {
            String query = "SELECT * FROM " + TABLE_REGISTERS + " WHERE " + ID_PROPRIETARIO + " = " + id;

            Cursor cursor = db.rawQuery(query, null);
            try{
                if (cursor.getCount() > 0)
                    flag = 1;

            }finally {
                cursor.close();
            }
        }
        if(flag==1)
            return true;
        else
            return false;
    }

//    DB removes

    public void removeBenfeitorias(String idProp){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_BENF, ID_BENF + " = " + idProp, null);
        db.close();
    }

    public void removePlant(String idProp){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_PLANT, ID_PLANT + " = " + idProp, null);
        db.close();
    }

    public void removeDocs(String idProp){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_DOCS, ID_DOC + " = " + idProp, null);
        db.close();
    }

    public void removePins(String idProp){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_LATLNG, ID_LATLNG + " = " + idProp, null);
        db.close();
    }

    public void removeDoc(String file){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_DOCS, PATH + " = " + file, null);
        db.close();
    }

//    Propriedades Utils

    public ArrayList<Double> getLats(){
        ArrayList<Double> lats = new ArrayList<Double>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + LAT + " FROM " + TABLE_LATLNG;

        Cursor cursor = db.rawQuery(query, null);
        try{
            if(cursor.moveToFirst()){
                do{
                    lats.add(Double.parseDouble(cursor.getString(0)));
                }while(cursor.moveToNext());
            }
        }finally{
                cursor.close();
        }
        db.close();
        return lats;
    }

    public ArrayList<Double> getLats(String idProp){
        ArrayList<Double> lats = new ArrayList<Double>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + LAT + " FROM " + TABLE_LATLNG + " WHERE " + ID_LATLNG + " = " + idProp;

        Cursor cursor = db.rawQuery(query, null);
        try{
            if(cursor.moveToFirst()){
                do{
                    lats.add(Double.parseDouble(cursor.getString(0)));
                }while(cursor.moveToNext());
            }
        }finally{
                cursor.close();
        }
        db.close();
        return lats;
    }

    public ArrayList<Double> getLngs(){
        ArrayList<Double> lngs = new ArrayList<Double>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + LNG + " FROM " + TABLE_LATLNG;

        Cursor cursor = db.rawQuery(query, null);
        try{
            if(cursor.moveToFirst()){
                do{
                    lngs.add(Double.parseDouble(cursor.getString(0)));
                }while(cursor.moveToNext());
            }
        }finally{
                cursor.close();
        }
        db.close();
        return lngs;
    }

    public ArrayList<Double> getLngs(String idProp){
        ArrayList<Double> lngs = new ArrayList<Double>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + LNG + " FROM " + TABLE_LATLNG + " WHERE " + ID_LATLNG + " = " + idProp;

        Cursor cursor = db.rawQuery(query, null);
        try{
            if(cursor.moveToFirst()){
                do{
                    lngs.add(Double.parseDouble(cursor.getString(0)));
                }while(cursor.moveToNext());
            }
        }finally{
                cursor.close();
        }
        db.close();
        return lngs;
    }

    public ArrayList<String> getIds(){
        ArrayList<String> ids = new ArrayList<String>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + ID_LATLNG + " FROM " + TABLE_LATLNG;

        Cursor cursor = db.rawQuery(query, null);

        int count = 0;
        try{
            if(cursor.moveToFirst()){
                do{
                    ids.add(cursor.getString(0));
                    count++;
                }while(cursor.moveToNext());
            }
        }finally{
                cursor.close();
        }
        db.close();
        return ids;
    }

    public ArrayList<String> getTexts(){
        ArrayList<String> texts = new ArrayList<String>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + TEXT_LATLNG + " FROM " + TABLE_LATLNG;

        Cursor cursor = db.rawQuery(query, null);

        int count = 0;
        try{
            if(cursor.moveToFirst()){
                do{
                    texts.add(cursor.getString(0));
                    count++;
                }while(cursor.moveToNext());
            }
        }finally{
                cursor.close();
        }
        db.close();
        return texts;
    }

    public String getStatus(String id){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + STATUS + " FROM " + TABLE_REGISTERS + " WHERE " + ID_PROPRIETARIO + " = " + id;
        String flag;
        Cursor cursor = db.rawQuery(query, null);
        try {
            cursor.moveToFirst();

            if (cursor.getString(0) == null)
                flag = "Indefinido";
            else
                flag = cursor.getString(0);
        }finally{
            cursor.close();
        }

        return flag;

    }

    public Boolean hasLatLng(String idProp){

        SQLiteDatabase db = this.getWritableDatabase();
        int flag=0;
        String query = "SELECT * FROM " + TABLE_LATLNG + " WHERE " + ID_LATLNG + " = " + idProp;
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.getCount() > 0)
                flag = 1;
        }finally{
            cursor.close();

        }
        if(flag==1)
            return true;
        else
            return false;

    }

    public void setUser(String user){

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER, null, null);

        ContentValues values = new ContentValues();

        values.put(USER, user);

        db.insert(TABLE_USER, null, values);
        db.close();

    }

    public String getUser(){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_USER;

        Cursor cursor = db.rawQuery(query, null);
        String user;
        try {
            cursor.moveToFirst();
            user = cursor.getString(1);
        }finally {
            cursor.close();
        }
        db.close();
        return user;

    }

    public String getNextID(){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + ID_PROPRIETARIO + " FROM " + TABLE_REGISTERS;

        Cursor cursor = db.rawQuery(query, null);

        String id = "0";
        try {
            if (cursor.getCount() > 0) {
                cursor.moveToLast();
                id = String.valueOf(Integer.parseInt(cursor.getString(0)) + 1);
            } else {
                id = "301000";
            }
        }finally{
                cursor.close();
        }

        db.close();

        return id;

    }
    public void removeTrash(){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT " + ID_LATLNG + " FROM " + TABLE_LATLNG;

        Cursor cursor = db.rawQuery(query, null);
        try{
            if(cursor.moveToFirst()){
                do{
                    String queryProp = "SELECT " + ID_PROP + " FROM " + TABLE_PROP + " WHERE " + ID_PROP + " = " + cursor.getString(0);
                    Cursor prop = db.rawQuery(queryProp, null);
                    try {
                        if (prop.getCount() == 0) {
                            db.delete(TABLE_LATLNG, ID_LATLNG + " = " + cursor.getString(0), null);
                        }
                    }finally{
                        prop.close();
                    }
                }while(cursor.moveToNext());
            }
        }finally{
                cursor.close();
        }
        db.close();

    }


}
