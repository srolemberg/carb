package br.com.samirrolemberg.carb;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.samirrolemberg.carb.adapter.CalibragemAdapter;
import br.com.samirrolemberg.carb.conn.DatabaseManager;
import br.com.samirrolemberg.carb.daos.DAOCalibragem;
import br.com.samirrolemberg.carb.model.Calibragem;
import br.com.samirrolemberg.carb.model.Dispositivo;
import br.com.samirrolemberg.carb.utils.C;
import br.com.samirrolemberg.carb.utils.U;

public class CalibragemActivity extends AppCompatActivity {

    private Dispositivo dispositivo;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private DAOCalibragem daoCalibragem;
    private CalibragemAdapter adapter;
    private RecyclerView rvCalibragem;
    private GridLayoutManager layoutManager;

    private List<Calibragem> calibragens;

    private TextView audioToolBar, videoToolBar;

    @Override
    protected void onStart() {
        super.onStart();
        C.getTracker().setScreenName(CalibragemActivity.class.getSimpleName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibragem);

        dispositivo = (Dispositivo) getIntent().getSerializableExtra("dispositivo");

        calibragens = new ArrayList<>();

        daoCalibragem = new DAOCalibragem(C.getContext());
        calibragens = daoCalibragem.listarTudo(dispositivo);
        DatabaseManager.getInstance().closeDatabase();

        adapter = new CalibragemAdapter(calibragens, CalibragemActivity.this);

        rvCalibragem = (RecyclerView) findViewById(R.id.rvCalibragem);

        layoutManager = new GridLayoutManager(CalibragemActivity.this, U.getNumeroColunas(), GridLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);

        rvCalibragem.setLayoutManager(layoutManager);
        rvCalibragem.setAdapter(adapter);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((TextView)toolbar.findViewById(R.id.tvTituloToolBar)).setText(dispositivo.getNome());
        audioToolBar = (TextView) toolbar.findViewById(R.id.tvLabelAudioToolBarCalibragem);
        videoToolBar = (TextView) toolbar.findViewById(R.id.tvLabelVideoToolBarCalibragem);

        U.atualizaMedia(calibragens, audioToolBar, videoToolBar, true);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exibirDialog(CalibragemActivity.this);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void exibirDialog(final Activity context){
        final View view = CalibragemActivity.this.getLayoutInflater().inflate(R.layout.dialog_add_calibragem, null);

        final Button btnPositive = (Button) view.findViewById(R.id.btnSalvarCalibragem);
        final Button btnNegative = (Button) view.findViewById(R.id.btnCancelarCalibragem);

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .setView(view)
                .setTitle("Informe os dados do Instrumento")
                .create();

        final Spinner spn = (Spinner) view.findViewById(R.id.spnInstrumento);
        ArrayAdapter<CharSequence> spnAdapter = ArrayAdapter.createFromResource(context,
                R.array.instrumentos_array, android.R.layout.simple_spinner_item);
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(spnAdapter);


        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText titulo = (EditText) view.findViewById(R.id.etTituloCalibragem);
                EditText descricao = (EditText) view.findViewById(R.id.etDescricaoCalibragem);
                EditText audio = (EditText) view.findViewById(R.id.etAudioCalibragem);
                EditText video = (EditText) view.findViewById(R.id.etVideoCalibragem);

                if (TextUtils.isEmpty(titulo.getText().toString())) {
                    Toast.makeText(context, "Você precisa especificar o titulo", Toast.LENGTH_LONG).show();
                    return;
                }
                if (spn.getSelectedItemPosition() == 0) {
                    Toast.makeText(context, "Você precisa especificar o Instrumento", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(audio.getText().toString())) {
                    Toast.makeText(context, "Você precisa especificar o valor do Audio", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(video.getText().toString())) {
                    Toast.makeText(context, "Você precisa especificar o valor do Video", Toast.LENGTH_LONG).show();
                    return;
                }

                dialog.dismiss();

                Calibragem calibragem = new Calibragem.Builder()
                        .withVideo(Integer.valueOf(video.getText().toString()))
                        .withTitulo(titulo.getText().toString())
                        .withDispositivo(dispositivo)
                        .withDescricao(descricao.getText().toString())
                        .withAudio(Integer.valueOf(audio.getText().toString()))
                        .withDataCriacao(new Date())
                        .withTipo(spn.getSelectedItemPosition())
                        .build();

                daoCalibragem = new DAOCalibragem(context);
                calibragem = daoCalibragem.buscar(daoCalibragem.inserir(calibragem, dispositivo.getId()), dispositivo);
                DatabaseManager.getInstance().closeDatabase();

                calibragens.add(calibragem);

                adapter.notifyDataSetChanged();

                rvCalibragem.smoothScrollToPosition(adapter.getItemCount());

                U.atualizaMedia(calibragens, audioToolBar, videoToolBar, true);

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

    public void exibirDialog(final Activity context, final Calibragem calibragem){
        final View view = CalibragemActivity.this.getLayoutInflater().inflate(R.layout.dialog_add_calibragem, null);

        final Button btnPositive = (Button) view.findViewById(R.id.btnSalvarCalibragem);
        final Button btnNegative = (Button) view.findViewById(R.id.btnCancelarCalibragem);

        final EditText titulo = (EditText) view.findViewById(R.id.etTituloCalibragem);
        final EditText descricao = (EditText) view.findViewById(R.id.etDescricaoCalibragem);
        final EditText audio = (EditText) view.findViewById(R.id.etAudioCalibragem);
        final EditText video = (EditText) view.findViewById(R.id.etVideoCalibragem);

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .setView(view)
                .setTitle("Modifique os dados do Instrumento")
                .create();

        final Spinner spn = (Spinner) view.findViewById(R.id.spnInstrumento);
        ArrayAdapter<CharSequence> spnAdapter = ArrayAdapter.createFromResource(context,
                R.array.instrumentos_array, android.R.layout.simple_spinner_item);
        spnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn.setAdapter(spnAdapter);

        //inicia os valores do dialog
        spn.setSelection(calibragem.getTipo());//seta a posição inicial
        titulo.setText(calibragem.getTitulo());
        descricao.setText(calibragem.getDescricao());
        audio.setText(calibragem.getAudio().toString());
        video.setText(calibragem.getVideo().toString());

        btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(titulo.getText().toString())) {
                    Toast.makeText(context, "Você precisa especificar o titulo", Toast.LENGTH_LONG).show();
                    return;
                }
                if (spn.getSelectedItemPosition() == 0) {
                    Toast.makeText(context, "Você precisa especificar o Instrumento", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(audio.getText().toString())) {
                    Toast.makeText(context, "Você precisa especificar o valor do Audio", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(video.getText().toString())) {
                    Toast.makeText(context, "Você precisa especificar o valor do Video", Toast.LENGTH_LONG).show();
                    return;
                }

                dialog.dismiss();

                Calibragem calibragem_nova = new Calibragem.Builder()
                        .withId(calibragem.getId())
                        .withVideo(Integer.valueOf(video.getText().toString()))
                        .withTitulo(titulo.getText().toString())
                        .withDispositivo(dispositivo)
                        .withDescricao(descricao.getText().toString())
                        .withAudio(Integer.valueOf(audio.getText().toString()))
                        .withUltimaAtualizacao(new Date())
                        .withTipo(spn.getSelectedItemPosition())
                        .build();

                daoCalibragem = new DAOCalibragem(context);
                calibragem_nova = daoCalibragem.buscar(new Long(calibragem_nova.getId()), dispositivo);
                DatabaseManager.getInstance().closeDatabase();

                Log.i("CALIBRAGEM", "CALIBRAGEM: " + calibragem_nova.toString());

                for (int i = 0; i < calibragens.size(); i++) {
                    if (calibragens.get(i).getId().equals(calibragem.getId())) {
                        calibragens.set(i, calibragem_nova);
                        break;
                    }
                }

                adapter.notifyDataSetChanged();

                U.atualizaMedia(calibragens, audioToolBar, videoToolBar, true);
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

}
