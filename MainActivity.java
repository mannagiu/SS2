package com.btree.giulia.myapplication2.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.btree.giulia.myapplication2.R;
import com.btree.giulia.myapplication2.myadapter.Myadapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    int count2=0;

    int i;
    int j;
    // definisco un ArrayList con il nome delle cartelle e/o file
    ArrayList<String> listp;


    //ARRAY DI IMMAGINI
    ArrayList<Integer> list_i ;;
    ArrayList<String> list_items;
    ArrayList<Myobject> arrayobj ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView im=(ImageView) findViewById(R.id.ima);
        im.setImageResource(R.drawable.addfolder);
        final ListView mylist = (ListView) findViewById(R.id.listv);
        listp=new ArrayList<>();
        list_i= new ArrayList<>();
        list_items=new ArrayList<>();
        arrayobj=new ArrayList<>();

        Myadapter myAdapter =new Myadapter(MainActivity.this, list_i, listp);

        Myobject o1 = new Myobject("dir p", "dir",false);
        Myobject o2 = new Myobject("dir2", "dir",false);
        Myobject o3 = new Myobject("file1", "file",false);


        //use >  int id = c.getInt("duration"); if you want get an int
        arrayobj.add(o1);
        arrayobj.add(o2);
        arrayobj.add(o3);

        //String[] nameproducts = new String[] { "dir1", "dir2", "dir3","file","dir4" };




        for ( i = 0; i < arrayobj.size(); i++) {
            listp.add(arrayobj.get(i).getNome());
        }

        for (j = 0; j < arrayobj.size(); j++) {
            if (arrayobj.get(j).getTipo() == "dir")
                list_i.add(R.drawable.folder);
            else
                list_i.add(R.drawable.file);

        }
        // recupero la lista dal
        // layout
        // creo e istruisco l'adattatore



        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listp);

        // inietto i dati
        mylist.setAdapter(myAdapter);
        mylist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        final Myadapter MyAdapter = myAdapter;
        mylist.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                count2=count2+1;
                mode.setTitle(count2+"items selected");
                list_items.add(listp.get(position));

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = mode.getMenuInflater();
                menuInflater.inflate(R.menu.menu, menu);
                return true;

            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch(item.getItemId()){
                    case R.id.delete_id:
                       /* for(String msg : list_items) {
                            finalMyAdapter.remove(msg, list_items);
                            finalMyAdapter.notifyDataSetChanged();
                        }*/
                        Toast.makeText(getBaseContext(),count2+"items removed",Toast.LENGTH_SHORT).show();
                        count2=0;
                        mode.finish();
                        return true;
                        //break;
                    default:
                        return false;
                }


                //return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });

        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adattatore, final View componente, int pos, long id) {
                // recupero il titolo memorizzato nella riga tramite l'ArrayAdapter

                if (arrayobj.get(pos).getTipo() == "dir") {
                    Intent pagina2 = new Intent(getApplicationContext(), Main2Activity.class);
                    startActivity(pagina2);

                }


            }

                    /*INSERIRE QUI DIRETTAMENTE IL FILE DA SCARICARE, RICHIESTA HTTP GIÀ IMPLEMENTATA
                    else{

                    }*/



/*
                PER USARE UN'ALTRA ACTIVITY DEVO CREARE UN INTENT e passargli
                //IL METODO getApplicationContext() , mettere una virgola
                e DOPO LA VIRGOLA METTERE IL NOME DELL'ACTIVITY SUCCESSIVA che abbiamo creato(il nome del file .java) E METTERE
                subito dopo -> .class*/
                //mi fa partire la seconda activity mettendo dentro startActivity il nome dell'intent creato
            });





        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                final View mView = getLayoutInflater().inflate(R.layout.dialog_newfolder, null);
                final EditText crea_cartella = (EditText) mView.findViewById(R.id.nome_cartella);

                mBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!crea_cartella.getText().toString().isEmpty())
                        {
                            Toast.makeText(MainActivity.this, "Cartella aggiunta", Toast.LENGTH_SHORT).show();
                            String s;
                            s = crea_cartella.getText().toString();
                            Myobject o=new Myobject(s,"dir",false);
                            arrayobj.add(o);
                            listp.add(s);
                            list_i.add(R.drawable.folder);

                            //aggiungere nel layout

                            //dovrei scriver il mio file json
                            //SCRITTURA FILE JSON
                            String state;
                            state= Environment.getExternalStorageState();


                            if(Environment.MEDIA_MOUNTED.equals(state))
                            {
                                //questo creerà una nuova cartella nello storage con il nome Myappfile
                                Environment e = new Environment();
                                File dir=new File(e.getExternalStorageDirectory().getAbsolutePath(),"/MyApp");
                                if(!dir.exists())
                                {
                                    dir.mkdirs();
                                }
                                File file=new File(dir,s+".json");

                                String message="ciao";
                                try {
                                    FileOutputStream fileOutputStream=new FileOutputStream(file);
                                    fileOutputStream.write(message.getBytes());
                                    fileOutputStream.close();

                                } catch (FileNotFoundException e2) {
                                    e2.printStackTrace();
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }


                            }
                            else {
                                Toast.makeText(getApplicationContext(),"SD card Not Found",Toast.LENGTH_LONG).show();
                            }








                        }
                        else
                            {
                            Toast.makeText(MainActivity.this, "Inserisci nome cartella", Toast.LENGTH_SHORT).show();

                            }


                    }
                });
                        mBuilder.setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                });



                              /*prova.setOnClickListener(new View.OnClickListener()){
                    public void onClick(View v) {
                        if (!crea_cartella.getText().toString().isEmpty()) {
                            Toast.makeText(MainActivity.this, R.string.new_folder, Toast.LENGTH_SHORT).show();
                            String s;
                            s=crea_cartella.getText().toString();
                            listp.add(s);
                            list_i.add(R.mipmap.ic_folder_black_24dp);
                            mylist.setAdapter(myAdapter);


                        } else
                            Toast.makeText(MainActivity.this, "Inserisci nome cartella", Toast.LENGTH_SHORT).show();


                    }
                });*/

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();


            }
        });


    }
/*
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.MENU_2:
                Toast.makeText(MainActivity.this, "Ancora niente", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.MENU_3:
                Toast.makeText(MainActivity.this, "Ancora niente", Toast.LENGTH_SHORT).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

   public void writeExternalStorage(View view){
        String state;
        state= Environment.getExternalStorageState();


        if(Environment.MEDIA_MOUNTED.equals(state))
        {
            File root= Environment.getExternalStorageDirectory();
            //questo creerà una nuova cartella nello storage con il nome Myappfile
            File dir=new File(root.getAbsolutePath()+"/MyAppFile");
            if(dir.exists())
            {
                dir.mkdir();
            }
            File file=new File(dir,"Mymessage.txt");

            String message="ciao";
            try {
                FileOutputStream fileOutputStream=new FileOutputStream(file);
                fileOutputStream.write(message.getBytes());
                fileOutputStream.close();



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        else {
            Toast.makeText(getApplicationContext(),"SD card Not Found",Toast.LENGTH_LONG).show();
        }

    }


public readExternalStorage(){

    File root= Environment.getExternalStorageDirectory();
    //questo creerà una nuova cartella nello storage con il nome Myappfile
    File dir=new File(root.getAbsolutePath()+"/MyAppFile");
    File file=new File(dir,"Mymessage.json");
    String message;
    try {
        FileInputStream fileInputStream=new FileInputStream(file);
        InputStreamReader inputStreamReader= new InputStreamReader(fileInputStream);

        StringBuffer stringBuffer=new StringBuffer();
        BufferedReader bufferedReader=new BufferedReader((inputStreamReader);
        while((message=bufferedReader.readLine())!=null){



            stringBuffer.append(message +"\n");




        }

        textview.setText(stringBuffer.toString());





    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }


}


*/


}

