package br.com.samirrolemberg.carb.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import br.com.samirrolemberg.carb.R;
import br.com.samirrolemberg.carb.adapter.DispositivoAdapter;
import br.com.samirrolemberg.carb.daos.DispositivoDAO;
import br.com.samirrolemberg.carb.helper.RealmHelper;
import br.com.samirrolemberg.carb.model.Dispositivo;
import br.com.samirrolemberg.carb.utils.CustomContext;
import br.com.samirrolemberg.carb.utils.Utils;
import io.realm.Realm;


public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private List<Dispositivo> dispositivos;
    private DispositivoAdapter adapter;
    private RecyclerView rvDispositivo;
    private GridLayoutManager layoutManager;

    @Override
    protected void onStart() {
        super.onStart();
        CustomContext.getTracker().setScreenName(MainActivity.class.getSimpleName());
        Log.w("CustomContext", "Status: isTablet() > " + Utils.isTablet());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivo);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exibirDialog(MainActivity.this);
            }
        });

        dispositivos = new DispositivoDAO(RealmHelper.getInstance()).findAll();

        adapter = new DispositivoAdapter(dispositivos, MainActivity.this);

        rvDispositivo = (RecyclerView) findViewById(R.id.rvDispositivos);

        layoutManager = new GridLayoutManager(MainActivity.this, Utils.getNumeroColunas(), GridLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);

        rvDispositivo.setLayoutManager(layoutManager);
        rvDispositivo.setAdapter(adapter);

    }

    public void exibirDialog(final Activity context) {
        final View view = MainActivity.this.getLayoutInflater().inflate(R.layout.dialog_add_dispositivo, null);

        final Button btnPositive = (Button) view.findViewById(R.id.btnSalvarDispositivo);
        final Button btnNegative = (Button) view.findViewById(R.id.btnCancelarDispositivo);


        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .setView(view)
                .setTitle(getString(R.string.main_act_dialog_titulo))
                .create();


        final Spinner spn = (Spinner) view.findViewById(R.id.spnDispositivo);
        ArrayAdapter<CharSequence> spnAdapter = ArrayAdapter.createFromResource(context,
                R.array.dispositivos_array, android.R.layout.simple_spinner_item);
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(spnAdapter);

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nome = (EditText) view.findViewById(R.id.etNomeDispositivo);
                if (spn.getSelectedItemPosition() == 0) {
                    Toast.makeText(context, R.string.main_act_dialog_erro_spinner, Toast.LENGTH_LONG).show();
                    YoYo.with(Techniques.Swing).duration(CustomContext.getContext().getResources().getInteger(R.integer.DURACAO_ANIMACAO)).playOn(spn);
                    return;
                }
                if (TextUtils.isEmpty(nome.getText().toString())) {
                    Toast.makeText(context, R.string.main_act_dialog_erro_nome, Toast.LENGTH_LONG).show();
                    YoYo.with(Techniques.Swing).duration(CustomContext.getContext().getResources().getInteger(R.integer.DURACAO_ANIMACAO)).playOn(nome);
                    return;
                }

                dialog.dismiss();

                Dispositivo dispositivo = new Dispositivo();
                dispositivo.setId(UUID.randomUUID().toString());
                dispositivo.setNome(nome.getText().toString());
                dispositivo.setDataCriacao(new Date());
                dispositivo.setTipo(spn.getSelectedItemPosition());

                Realm realm = RealmHelper.getInstance();

                realm.beginTransaction();
                realm.copyToRealm(dispositivo);
                realm.commitTransaction();

                dispositivos.add(dispositivo);

                Intent intent = new Intent(context, CalibragemActivity.class);
                intent.putExtra(getString(R.string.constante_dispositivo), dispositivo);
                startActivityForResult(intent, CustomContext.getContext().getResources().getInteger(R.integer.REQUEST__ATUALIZACAO_LISTA_DISPOSITIVO_ACT));

                adapter.notifyDataSetChanged();

                rvDispositivo.smoothScrollToPosition(adapter.getItemCount());
            }
        });

        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void exibirDialog(final Activity context, final Dispositivo dispositivo) {
        final View view = MainActivity.this.getLayoutInflater().inflate(R.layout.dialog_add_dispositivo, null);

        final Button btnPositive = (Button) view.findViewById(R.id.btnSalvarDispositivo);
        final Button btnNegative = (Button) view.findViewById(R.id.btnCancelarDispositivo);
        final EditText nome = (EditText) view.findViewById(R.id.etNomeDispositivo);


        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .setView(view)
                .setTitle(getString(R.string.main_act_dialog_titulo_editar))
                .create();


        final Spinner spn = (Spinner) view.findViewById(R.id.spnDispositivo);
        ArrayAdapter<CharSequence> spnAdapter = ArrayAdapter.createFromResource(context,
                R.array.dispositivos_array, android.R.layout.simple_spinner_item);
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn.setAdapter(spnAdapter);

        //inicia os valores do dialog
        spn.setSelection(dispositivo.getTipo());//seta a posição inicial
        nome.setText(dispositivo.getNome());


        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spn.getSelectedItemPosition() == 0) {
                    Toast.makeText(context, R.string.main_act_dialog_erro_spinner, Toast.LENGTH_LONG).show();
                    YoYo.with(Techniques.Swing).duration(CustomContext.getContext().getResources().getInteger(R.integer.DURACAO_ANIMACAO)).playOn(spn);
                    return;
                }
                if (TextUtils.isEmpty(nome.getText().toString())) {
                    Toast.makeText(context, R.string.main_act_dialog_erro_nome, Toast.LENGTH_LONG).show();
                    YoYo.with(Techniques.Swing).duration(CustomContext.getContext().getResources().getInteger(R.integer.DURACAO_ANIMACAO)).playOn(nome);
                    return;
                }

                dialog.dismiss();


                Realm realm = RealmHelper.getInstance();

                realm.beginTransaction();

                dispositivo.setNome(nome.getText().toString());
                dispositivo.setUltimaAtualizacao(new Date());
                dispositivo.setTipo(spn.getSelectedItemPosition());

                realm.copyToRealmOrUpdate(dispositivo);
                realm.commitTransaction();

                adapter.notifyDataSetChanged();

            }
        });

        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_dispositivo_sobre_carb) {
//            CustomContext.getTracker().send(new HitBuilders.EventBuilder()
//                    .setCategory("Menu Principal")
//                    .setAction("Sobre CARB")
//                    .setLabel("abrir")
//                    .build());

            Intent intent = new Intent(MainActivity.this, SobreActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CustomContext.getContext().getResources().getInteger(R.integer.REQUEST__ATUALIZACAO_LISTA_DISPOSITIVO_ACT)) {
            if (!dispositivos.isEmpty()) {
                adapter.notifyDataSetChanged();
            }
            if (fab.getVisibility() != View.VISIBLE) {
                fab.show();
            }
        }
    }
}
