package com.giulia.miapplicazionediprova;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ImageView im=(ImageView) findViewById(R.id.ima);
        im.setImageResource(R.drawable.addfolder);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Main2Activity.this);
                final View mView = getLayoutInflater().inflate(R.layout.dialog_newfolder, null);
                final EditText crea_cartella = (EditText) mView.findViewById(R.id.nome_cartella);

                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!crea_cartella.getText().toString().isEmpty())
                        {
                            Toast.makeText(Main2Activity.this, "Cartella aggiunta", Toast.LENGTH_SHORT).show();
                            String s;
                            s = crea_cartella.getText().toString();
                            //Myobject o=new Myobject(s,"dir",false);
                            //arrayobj.add(o);
                           // listp.add(s);
                          //  list_i.add(R.drawable.folder);

                            JSONObject newone = new JSONObject();
                            JSONArray arrayinterno = new JSONArray();
                            try {
                                newone.put(s, arrayinterno);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            //IN QUESTO CASO MI TROVO NELLA SECONDA ACTIVITY, A SECONDA DELLA CARTELLA SU CUI CLICCO
                            //NELLA PRIMA ACTIVITY , SALVO IL NOME DELL'ELEMENTO SU CUI CLICCO IN QUALCHE MODO E LO PASSO
                            //NELLA SECONDA ACTIVITY
                            //aggiungo all' array json la cartella
                            parse.put(newone);
                            //aggiorno il file
                            String ciao;
                            ciao = parse.toString();
                            try {

                                fOut = new FileOutputStream(filedirectory);

                                fOut.write(ciao.getBytes());

                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                try {
                                    fOut.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            //Rileggo tutto
                            try {
                                stream = new FileInputStream(filedirectory);

                                try {
                                    temp = "";
                                    int c;
                                    while ((c = stream.read()) != -1) {
                                        temp = temp + Character.toString((char) c);
                                    }

                                    tv.setText(temp);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    stream.close();
                                }
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            //Quello che leggo lo metto in un array json


                            try {
                                parse = new JSONArray(temp);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            JSONObject obj = new JSONObject();

                            for (i = 0; i < parse.length(); i++) {


                                try {
                                    //Estraggo l' oggetto json dall'array, in particolare l'i-esimo oggetto json
                                    obj = parse.getJSONObject(i);
                                    //ottengo le chiavi che ci sono nel mio array json
                                    Iterator iterator = obj.keys();
                                    while (iterator.hasNext()) {
                                        String key = (String) iterator.next();
                                        //uso le chiavi per aggiungerle alla lista che mi serve poi per rappresentare il tutto
                                        listp.add(key);
                                        list_i.add(R.drawable.folder);

                                    }
                                    //inietto i dati nell' Adapter
                                    //  mylist.setAdapter(myAdapter);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }
                    }
                });

                mBuilder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });


                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }
        });






    }


