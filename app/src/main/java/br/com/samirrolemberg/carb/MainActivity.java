package br.com.samirrolemberg.carb;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.daimajia.androidanimations.library.BaseViewAnimator;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.analytics.HitBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.samirrolemberg.carb.adapter.DispositivoAdapter;
import br.com.samirrolemberg.carb.conn.DatabaseManager;
import br.com.samirrolemberg.carb.daos.DAODispositivo;
import br.com.samirrolemberg.carb.model.Dispositivo;
import br.com.samirrolemberg.carb.utils.C;
import br.com.samirrolemberg.carb.utils.U;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DAODispositivo daoDispositivo;
    private List<Dispositivo> dispositivos;
    private DispositivoAdapter adapter;
    private RecyclerView rvDispositivo;
    private GridLayoutManager layoutManager;

    @Override
    protected void onStart() {
        super.onStart();
        C.getTracker().setScreenName(MainActivity.class.getSimpleName());
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

        dispositivos = new ArrayList<>();

        daoDispositivo = new DAODispositivo(MainActivity.this);
        dispositivos = daoDispositivo.listarTudo();
        DatabaseManager.getInstance().closeDatabase();

        adapter = new DispositivoAdapter(dispositivos, MainActivity.this);

        rvDispositivo = (RecyclerView) findViewById(R.id.rvDispositivos);

        layoutManager = new GridLayoutManager(MainActivity.this, U.getNumeroColunas(), GridLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);

        rvDispositivo.setLayoutManager(layoutManager);
        rvDispositivo.setAdapter(adapter);

    }

    public void exibirDialog(final Activity context){
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
                    YoYo.with(Techniques.Swing).duration(350).playOn(spn);
                    return;
                }
                if (TextUtils.isEmpty(nome.getText().toString())) {
                    Toast.makeText(context, R.string.main_act_dialog_erro_nome, Toast.LENGTH_LONG).show();
                    YoYo.with(Techniques.Swing).duration(350).playOn(nome);
                    return;
                }

                dialog.dismiss();

                Dispositivo dispositivo = new Dispositivo();
                dispositivo.setNome(nome.getText().toString());
                dispositivo.setDataCriacao(new Date());
                dispositivo.setTipo(spn.getSelectedItemPosition());

                daoDispositivo = new DAODispositivo(context);
                dispositivo = daoDispositivo.buscar(daoDispositivo.inserir(dispositivo));
                DatabaseManager.getInstance().closeDatabase();

                dispositivos.add(dispositivo);

                Intent intent = new Intent(context, CalibragemActivity.class);
                intent.putExtra(getString(R.string.constante_dispositivo), dispositivo);
                startActivity(intent);

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

    public void exibirDialog(final Activity context, final Dispositivo dispositivo){
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
                    YoYo.with(Techniques.Swing).duration(350).playOn(spn);
                    return;
                }
                if (TextUtils.isEmpty(nome.getText().toString())) {
                    Toast.makeText(context, R.string.main_act_dialog_erro_nome, Toast.LENGTH_LONG).show();
                    YoYo.with(Techniques.Swing).duration(350).playOn(nome);
                    return;
                }

                dialog.dismiss();

                dispositivo.setNome(nome.getText().toString());
                dispositivo.setUltimaAtualizacao(new Date());
                dispositivo.setTipo(spn.getSelectedItemPosition());

                daoDispositivo = new DAODispositivo(context);
                daoDispositivo.atualiza(dispositivo);
                DatabaseManager.getInstance().closeDatabase();

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

        if (item.getItemId() == R.id.menu_dispositivo_configurar) {
            Toast.makeText(MainActivity.this, "click Menu Configurar", Toast.LENGTH_LONG).show();
            C.getTracker().send(new HitBuilders.EventBuilder()
                    .setCategory("Menu Principal")
                    .setAction("Configurar")
                    .setLabel("abrir")
                    .build());
            return true;
        }
        if (item.getItemId() == R.id.menu_dispositivo_sobre_carb) {
            Toast.makeText(MainActivity.this, "click Menu CARB", Toast.LENGTH_LONG).show();
            C.getTracker().send(new HitBuilders.EventBuilder()
                    .setCategory("Menu Principal")
                    .setAction("Sobre CARB")
                    .setLabel("abrir")
                    .build());
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
