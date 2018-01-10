package horusgeo.eco101;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class InitialActivity extends AppCompatActivity {

    Button addCadastro;
    Button editCadastro;
    Button deleteCadastro;
    Button sendCadastro;
    FloatingActionButton fab;
    DBHandler db;
    TextView warnInitialText;
    String tipo;
    String texto;
    String user;

    Register cadastro;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectDiskReads()
//                .detectDiskWrites()
//                .detectNetwork()   // or .detectAll() for all detectable problems
//                .penaltyLog()
//                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

        addCadastro = (Button) findViewById(R.id.newRegister);
        editCadastro = (Button) findViewById(R.id.editRegister);
        deleteCadastro = (Button) findViewById(R.id.deleteRegister);
        sendCadastro = (Button) findViewById(R.id.sendRegister);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        Intent intent = getIntent();
        tipo = intent.getStringExtra("tipo");
        texto = intent.getStringExtra("texto");

        db = new DBHandler(this, null, null, 1);

        user = db.getUser();

        warnInitialText = (TextView) findViewById(R.id.warnInitialText);

        if(tipo.equals("ok")) {
            warnInitialText.setText(texto);
            warnInitialText.setTextColor(Color.GREEN);
        }else if(tipo.equals("start")){
            warnInitialText.setText(texto);
            warnInitialText.setTextColor(Color.BLUE);
        }else if(tipo.equals("cancel")){
            warnInitialText.setText(texto);
            warnInitialText.setTextColor(Color.RED);
        }

        cadastro = new Register();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InitialActivity.this, MappingActivity.class);
                intent.putExtra("idProp", "-1");
                startActivity(intent);
            }
        });

        addCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callPropDialog();


            }
        });

        editCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callEditPropertyDialog();
            }
        });

        deleteCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDeletePropertyDialog();
            }
        });

        sendCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InitialActivity.this);
                //builder.setTitle("!!! ATENÇÃO !!!");
                builder.setMessage("Esta função enviará os dados para o Sistema Web!\n" +
                        "Certifique-se de que todos os dados estão corretos!\n" +
                        "É necessário uma conexão com a internet!")
                        .setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SendDB sendDB = new SendDB(InitialActivity.this);
                                sendDB.execute((Void) null);
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                TextView title = new TextView(InitialActivity.this);
                title.setText("!!! ATENÇÃO !!!");
                title.setGravity(Gravity.CENTER_HORIZONTAL);
                title.setTextSize(30);
                title.setTextColor(Color.RED);

                builder.setCustomTitle(title);

                Dialog d = builder.show();

                TextView textView = (TextView) d.findViewById(android.R.id.message);

                textView.setGravity(Gravity.CENTER);

            }
        });

        FloatingActionButton fab_cancel = (FloatingActionButton) findViewById(R.id.fab_cancel);
        assert fab_cancel != null;
        fab_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InitialActivity.this);
                builder.setMessage("Você realmente deseja sair?")
                        .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                InitialActivity.this.finishAffinity();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
            }
        });

        checkfForInconsistency();


    }

    @Override
    public void onBackPressed(){

    }

    public void callEditPropertyDialog(){
        final List<Register> registers = db.getAllIdNameRegisters();

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(InitialActivity.this);
//        builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Selecione o Proprietário");


        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                InitialActivity.this,
                android.R.layout.select_dialog_singlechoice){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);

                if (registers.get(position).getRegisterStatus() == 1) {
                    text.setTextColor(Color.BLACK);
                } else {
                    text.setTextColor(Color.RED);
                }

                return view;
            }
        };

        for(Register temp : registers){
            arrayAdapter.add(temp.get_nome_proprietario());
        }

        builderSingle.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = registers.get(which).get_id_prop();
                        Intent intent = new Intent(InitialActivity.this, CadastroActivity.class);
                        intent.putExtra("tipo", "edit");
                        intent.putExtra("string", strName);
                        startActivity(intent);
                    }
                });
        builderSingle.show();

    }

    public void callDeletePropertyDialog(){
        final List<Register> registers = db.getAllIdNameRegisters();

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(InitialActivity.this);
//        builderSingle.setIcon(R.drawable.ic_launcher);
        builderSingle.setTitle("Selecione o Proprietário");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                InitialActivity.this,
                android.R.layout.select_dialog_singlechoice);

        for(Register temp : registers){
            arrayAdapter.add(temp.get_nome_proprietario());
        }
        builderSingle.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(
                arrayAdapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = registers.get(which).get_id_prop();
                        db.deleteRegister(strName);
                        dialog.dismiss();
                    }
                });
        builderSingle.show();

    }

    private void callPropDialog(){
        final AlertDialog.Builder builderSingle = new AlertDialog.Builder(InitialActivity.this);
//        builderSingle.setTitle("Defina o nome e o número de identificação do proprietário:");
        builderSingle.setTitle("Defina o nome do proprietário:");
        builderSingle.setCancelable(false);
        LayoutInflater inflater = getLayoutInflater();
        final View rootView = inflater.inflate(R.layout.latlng_dialog, null);
        final EditText nameText = (EditText) rootView.findViewById(R.id.latlngNameText);;
//        final EditText idText = (EditText) rootView.findViewById(R.id.latlngIdText);

        builderSingle.setView(rootView);

        builderSingle.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (nameText.getText().toString().equals("")) {
                    callPropDialog();
//                } else if (idText.getText().toString().equals("")) {
//                    callPropDialog();
                } else {
                    cadastro.set_nome_proprietario(nameText.getText().toString());
//                    cadastro.set_id_prop(idText.getText().toString());
                    cadastro.set_id_prop(db.getNextID());
                    cadastro.set_data_visita(DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE).format(new Date()));
                    cadastro.set_horario_chegada(DateFormat.getTimeInstance(DateFormat.SHORT, Locale.FRANCE).format(new Date()));
                    //cadastro.set_responsavel(Responsavel.TESTE);
                    db.addRegister(cadastro);
                    db.addProp(cadastro);
                    db.addConj(cadastro);
                    db.addEndRes(cadastro);
                    db.addEndObj(cadastro);
                    db.addIdProp(cadastro);
                    db.addDesc(cadastro);
                    db.addObs(cadastro);

                    Intent intent = new Intent(InitialActivity.this, CadastroActivity.class);
                    intent.putExtra("tipo", "edit");
                    intent.putExtra("string", cadastro.get_id_prop());
                    startActivity(intent);

                }
            }
        });
        builderSingle.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderSingle.show();
    }




    private class SendDB extends AsyncTask<Void, Integer, Boolean> {

        private ProgressDialog dialogProgress;

        SendDB(Activity activity){
            this.dialogProgress = new ProgressDialog(activity);
        }

        protected void onPreExecute() {
            this.dialogProgress.setMessage("Enviando Cadastros! Por Favor, aguarde!");
            this.dialogProgress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.dialogProgress.setProgress(0);
            this.dialogProgress.show();
            this.dialogProgress.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(Void... params){


            FTPClient client = new FTPClient();
            Boolean teste;
            ArrayList<String> list = db.getAllDocs();
            int ntables = db.getNTables();
            double numFiles = list.size() + ntables;

            try{
                client.connect("ftp.horusgeo.com.br", 21);
                teste = client.login("horusgeo", "eco101web");

                client.enterLocalPassiveMode();

                client.setFileType(FTP.BINARY_FILE_TYPE, FTP.BINARY_FILE_TYPE);
                client.setFileTransferMode(FTP.BINARY_FILE_TYPE);
                int count = 0;
                Boolean result = false;
                double progress = 0.0;
                while(count < ntables){
                    File file = db.generateCSV(count);
                    FileInputStream inputStream = new FileInputStream(file);
                    String[] name = file.getPath().split("/");
                    result = client.storeFile("/public_ftp/incoming/"+ user + "_" + name[name.length-1], inputStream);
                    inputStream.close();
                    count++;
                    progress = 100*(count/numFiles);
                    publishProgress((int)(progress));
                }
                count = 0;

                while(count < list.size()){
                    File file = new File(list.get(count));
                    FileInputStream inputStream = new FileInputStream(file);
                    String[] name = file.getPath().split("/");
                    result = client.storeFile("/public_ftp/incoming/img/"+ name[name.length-1], inputStream);
                    inputStream.close();
                    count++;
                    progress = 100*((count+ntables)/numFiles);
                    publishProgress((int)(progress));
                }

                client.logout();

                return result;

            }catch (IOException ex) {
                ex.printStackTrace();
            }
            return false;
        }
        @Override
        protected void onProgressUpdate(Integer... progress) {

            this.dialogProgress.setProgress(progress[0]);
        }



        protected void onPostExecute(Boolean result) {
            if (dialogProgress.isShowing()) {
                dialogProgress.dismiss();
            }
            Toast toast;
            if(result){
                toast = Toast.makeText(getApplicationContext(), "Upload ok!", Toast.LENGTH_LONG);
            }else{
                toast = Toast.makeText(getApplicationContext(), "Upload not ok!", Toast.LENGTH_LONG);
            }
            toast.show();

        }

    }

    public void checkfForInconsistency(){
        db.removeTrash();
    }



}
