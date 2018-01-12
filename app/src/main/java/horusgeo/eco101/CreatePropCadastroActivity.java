package horusgeo.eco101;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import com.getbase.floatingactionbutton.FloatingActionButton;

public class CreatePropCadastroActivity extends AppCompatActivity {

    WebView myWebView;

    FloatingActionButton fabPointsCancel;
    FloatingActionButton fabPointsNew;
    FloatingActionButton fabPointsOk;
    FloatingActionButton fabPose;

    String idProp;

    DBHandler db;

    Register cadastro;

    Boolean criarProp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_prop_cadastro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent = getIntent();

        idProp = intent.getStringExtra("idProp");

        db = new DBHandler(this, null, null, 1);

        cadastro = new Register();

        if(!idProp.equals("-1"))
            cadastro = db.getRegister(idProp);

        myWebView = (WebView) findViewById(R.id.mapView);
        assert myWebView != null;
        myWebView.loadUrl("file:///android_asset/www/map.html");

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setAllowFileAccess(true);

        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url){
                callCheckDialog();
            }
        });

        myWebView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });

        myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");

        fabPointsCancel = (FloatingActionButton) findViewById(R.id.pointsCancel);
        fabPointsNew = (FloatingActionButton) findViewById(R.id.pointsNew);
        fabPointsOk = (FloatingActionButton) findViewById(R.id.pointsOk);
        fabPose = (FloatingActionButton) findViewById(R.id.poseFab);


        fabPointsNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myWebView.loadUrl("javascript:clickPoints()");
            }
        });

        fabPointsOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipo;
                if(cadastro.getTipoLatLng()!=null)
                    tipo = cadastro.getTipoLatLng();
                else
                    tipo = "Indefinido";
                myWebView.loadUrl("javascript:createProperty(" + cadastro.get_id_prop() + ", '" + cadastro.get_nome_proprietario() + "', '" + tipo + "')");
            }
        });

        fabPointsCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myWebView.loadUrl("javascript:clearPoints()");
                Intent intent = new Intent(CreatePropCadastroActivity.this, CadastroActivity.class);
                intent.putExtra("tipo", "edit");
                intent.putExtra("string", idProp);
                startActivity(intent);

            }
        });

        fabPose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myWebView.loadUrl("javascript:findLocation()");
            }
        });



    }

    private void callCheckDialog(){

        if(db.hasLatLng(idProp)){
            AlertDialog.Builder builder = new AlertDialog.Builder(CreatePropCadastroActivity.this);
            //builder.setTitle("!!! ATENÇÃO !!!");
            builder.setMessage("Este proprietário já possui uma propriedade cadastrada!\n" +
                    "Você deseja MANTER esta propriedade ou CRIAR uma nova?")
                    .setPositiveButton("CRIAR", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            fabPointsCancel.setVisibility(View.INVISIBLE);
                            fabPointsCancel.setClickable(false);

                            db.removePins(idProp);
                            populateMap();
                            dialog.dismiss();

                        }
                    })
                    .setNegativeButton("MANTER", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            fabPointsNew.setVisibility(View.INVISIBLE);
                            fabPointsNew.setClickable(false);

                            fabPointsOk.setVisibility(View.INVISIBLE);
                            fabPointsOk.setClickable(false);
                            criarProp = false;
                            populateMap();
                            dialog.dismiss();
                        }
                    });
            TextView title = new TextView(CreatePropCadastroActivity.this);
            title.setText("!!! ATENÇÃO !!!");
            title.setGravity(Gravity.CENTER_HORIZONTAL);
            title.setTextSize(30);
            title.setTextColor(Color.RED);

            builder.setCustomTitle(title);

            Dialog d = builder.show();

            TextView textView = (TextView) d.findViewById(android.R.id.message);

            textView.setGravity(Gravity.CENTER);


        }else{
            populateMap();
        }


    }

    public class WebAppInterface {
        Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
            mContext = c;
        }


        @JavascriptInterface
        public void callBackPropriedade(float[] latjs, float[] lngjs) {

            Double[] lat = new Double[latjs.length];
            Double[] lng = new Double[lngjs.length];
            for (int i = 0; i < lat.length; i++) {
                lat[i] = Double.valueOf(String.valueOf(latjs[i]));
                lng[i] = Double.valueOf(String.valueOf(lngjs[i]));
            }
            ArrayList<Double> lats = new ArrayList<Double>(Arrays.asList(lat));
            ArrayList<Double> lngs = new ArrayList<Double>(Arrays.asList(lng));

            cadastro.setLat(lats);
            cadastro.setLng(lngs);

            db.addLatLng(cadastro);

            Intent intent = new Intent(CreatePropCadastroActivity.this, CadastroActivity.class);
            intent.putExtra("tipo", "edit");
            intent.putExtra("string", idProp);
            startActivity(intent);

        }
    }


    public void populateMap(){

        ArrayList<Double> lat = db.getLats();
        ArrayList<Double> lng = db.getLngs();
        ArrayList<String> ids = db.getIds();
        ArrayList<String> texts = db.getTexts();
        String idBck = "a";

        int tam = lat.size();

        myWebView.loadUrl("javascript:removeLayers()");

        for (int i = 0; i < tam; i++){
            if(ids.get(i).equals("-1")){
                myWebView.loadUrl("javascript:populatePin('" + texts.get(i) + "', " + lat.get(i) + ", " + lng.get(i) + " )");
            }else{
                if(ids.get(i).equals(idBck)) {
                    myWebView.loadUrl("javascript:continueProp(" + lat.get(i) + ", " + lng.get(i) + " )");
                }else{
                    idBck = ids.get(i);
                    myWebView.loadUrl("javascript:newProp('" + ids.get(i) + "', " + lat.get(i) + ", " + lng.get(i) + ", '" + texts.get(i) + "','" + db.getStatus(ids.get(i)) + "')");
                }
            }
        }

        myWebView.loadUrl("javascript:loadImg('/storage/sdcard1/www')");
        myWebView.loadUrl("javascript:loadImg('/storage/extSdCard/www')");
        myWebView.loadUrl("javascript:loadKml()");
        myWebView.loadUrl("javascript:addProp()");

        if(criarProp)
            myWebView.loadUrl("javascript:startCentralPin()");

    }

}


