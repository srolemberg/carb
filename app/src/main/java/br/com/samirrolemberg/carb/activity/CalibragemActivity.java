package br.com.samirrolemberg.carb.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import br.com.samirrolemberg.carb.daos.CalibragemDAO;
import br.com.samirrolemberg.carb.helper.RealmHelper;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Date;
import java.util.List;

import br.com.samirrolemberg.carb.R;
import br.com.samirrolemberg.carb.adapter.CalibragemAdapter;
import br.com.samirrolemberg.carb.model.Calibragem;
import br.com.samirrolemberg.carb.model.Dispositivo;
import br.com.samirrolemberg.carb.utils.CustomContext;
import br.com.samirrolemberg.carb.utils.Utils;
import io.realm.Realm;


public class CalibragemActivity extends AppCompatActivity {

    private Dispositivo dispositivo;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private CalibragemAdapter adapter;
    private RecyclerView rvCalibragem;
    private GridLayoutManager layoutManager;

    private List<Calibragem> calibragens;

    private TextView audioToolBar, videoToolBar;

    @Override
    protected void onStart() {
        super.onStart();
        CustomContext.getTracker().setScreenName(CalibragemActivity.class.getSimpleName());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibragem);

        setResult(CustomContext.getContext().getResources().getInteger(R.integer.RESULT__ATUALIZACAO_LISTA_DISPOSITIVO_ACT));

        dispositivo = (Dispositivo) getIntent().getSerializableExtra(getString(R.string.constante_dispositivo));
        calibragens = new CalibragemDAO(RealmHelper.getInstance()).findAll();

        adapter = new CalibragemAdapter(calibragens, CalibragemActivity.this);

        rvCalibragem = (RecyclerView) findViewById(R.id.rvCalibragem);

        layoutManager = new GridLayoutManager(CalibragemActivity.this, Utils.getNumeroColunas(), GridLayoutManager.VERTICAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);

        rvCalibragem.setLayoutManager(layoutManager);
        rvCalibragem.setAdapter(adapter);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((TextView)toolbar.findViewById(R.id.tvTituloToolBar)).setText(dispositivo.getNome());
        audioToolBar = (TextView) toolbar.findViewById(R.id.tvLabelAudioToolBarCalibragem);
        videoToolBar = (TextView) toolbar.findViewById(R.id.tvLabelVideoToolBarCalibragem);

        Utils.atualizaMedia(calibragens, audioToolBar, videoToolBar, true);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exibirDialog(CalibragemActivity.this);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //sobreescreve o metodo do BACK na ActionBar para executar o Back do dispositivi
        //para sempre executar o OnActivityResult na outra tela
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    private void exibirDialog(final Activity context){
        final View view = CalibragemActivity.this.getLayoutInflater().inflate(R.layout.dialog_add_calibragem, null);

        final Button btnPositive = (Button) view.findViewById(R.id.btnSalvarCalibragem);
        final Button btnNegative = (Button) view.findViewById(R.id.btnCancelarCalibragem);

        final AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .setView(view)
                .setTitle(getString(R.string.calibragem_act_dialog_titulo))
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
                    Toast.makeText(context, R.string.calibragem_act_dialog_erro_titulo, Toast.LENGTH_LONG).show();
                    YoYo.with(Techniques.Swing).duration(CustomContext.getContext().getResources().getInteger(R.integer.DURACAO_ANIMACAO)).playOn(titulo);
                    return;
                }
                if (spn.getSelectedItemPosition() == 0) {
                    Toast.makeText(context, R.string.calibragem_act_dialog_erro_spinner, Toast.LENGTH_LONG).show();
                    YoYo.with(Techniques.Swing).duration(CustomContext.getContext().getResources().getInteger(R.integer.DURACAO_ANIMACAO)).playOn(spn);
                    return;
                }
                if (TextUtils.isEmpty(audio.getText().toString())) {
                    Toast.makeText(context, R.string.calibragem_act_dialog_erro_audio, Toast.LENGTH_LONG).show();
                    YoYo.with(Techniques.Swing).duration(CustomContext.getContext().getResources().getInteger(R.integer.DURACAO_ANIMACAO)).playOn(audio);
                    return;
                }
                if (TextUtils.isEmpty(video.getText().toString())) {
                    Toast.makeText(context, R.string.calibragem_act_dialog_erro_video, Toast.LENGTH_LONG).show();
                    YoYo.with(Techniques.Swing).duration(CustomContext.getContext().getResources().getInteger(R.integer.DURACAO_ANIMACAO)).playOn(video);
                    return;
                }

                dialog.dismiss();


                Calibragem calibragem = new Calibragem();
                        calibragem.setVideo(Integer.valueOf(video.getText().toString()));
                        calibragem.setTitulo(titulo.getText().toString());
                        calibragem.setDispositivo(dispositivo);
                        calibragem.setDescricao(descricao.getText().toString());
                        calibragem.setAudio(Integer.valueOf(audio.getText().toString()));
                        calibragem.setDataCriacao(new Date());
                        calibragem.setTipo(spn.getSelectedItemPosition());

	            Realm realm = RealmHelper.getInstance();

	            realm.beginTransaction();
	            realm.copyToRealm(calibragem);
                realm.commitTransaction();

                calibragens.add(calibragem);

                adapter.notifyDataSetChanged();

                rvCalibragem.smoothScrollToPosition(adapter.getItemCount());

                Utils.atualizaMedia(calibragens, audioToolBar, videoToolBar, true);

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
                .setTitle(getString(R.string.calibragem_act_dialog_titulo_editar))
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
                    Toast.makeText(context, R.string.calibragem_act_dialog_erro_titulo, Toast.LENGTH_LONG).show();
                    YoYo.with(Techniques.Swing).duration(CustomContext.getContext().getResources().getInteger(R.integer.DURACAO_ANIMACAO)).playOn(titulo);
                    return;
                }
                if (spn.getSelectedItemPosition() == 0) {
                    Toast.makeText(context, R.string.calibragem_act_dialog_erro_spinner, Toast.LENGTH_LONG).show();
                    YoYo.with(Techniques.Swing).duration(CustomContext.getContext().getResources().getInteger(R.integer.DURACAO_ANIMACAO)).playOn(spn);
                    return;
                }
                if (TextUtils.isEmpty(audio.getText().toString())) {
                    Toast.makeText(context, R.string.calibragem_act_dialog_erro_audio, Toast.LENGTH_LONG).show();
                    YoYo.with(Techniques.Swing).duration(CustomContext.getContext().getResources().getInteger(R.integer.DURACAO_ANIMACAO)).playOn(audio);
                    return;
                }
                if (TextUtils.isEmpty(video.getText().toString())) {
                    Toast.makeText(context, R.string.calibragem_act_dialog_erro_video, Toast.LENGTH_LONG).show();
                    YoYo.with(Techniques.Swing).duration(CustomContext.getContext().getResources().getInteger(R.integer.DURACAO_ANIMACAO)).playOn(video);
                    return;
                }

                dialog.dismiss();

	            Realm realm = RealmHelper.getInstance();

	            realm.beginTransaction();

                calibragem.setVideo(Integer.valueOf(video.getText().toString()));
                calibragem.setTitulo(titulo.getText().toString());
                calibragem.setDescricao(descricao.getText().toString());
                calibragem.setAudio(Integer.valueOf(audio.getText().toString()));
                calibragem.setUltimaAtualizacao(new Date());
                calibragem.setTipo(spn.getSelectedItemPosition());


	            realm.copyToRealmOrUpdate(calibragem);
	            realm.commitTransaction();

                adapter.notifyDataSetChanged();

                Utils.atualizaMedia(calibragens, audioToolBar, videoToolBar, true);
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
