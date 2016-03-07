package br.com.samirrolemberg.carb.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import br.com.samirrolemberg.carb.R;

public class SobreActivity extends AppCompatActivity {

    private Toolbar toolbar;


    private View sobre, dev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        init();

        dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = SobreActivity.this.getLayoutInflater().inflate(R.layout.dialog_sobre_dev, null);

                final AlertDialog dialog = new AlertDialog.Builder(SobreActivity.this)
                        .setCancelable(true)
                        .setView(view)
                        .create();

                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        });
        sobre.setOnClickListener(clickLayoutSobre());
    }

    @NonNull
    private View.OnClickListener clickLayoutSobre() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = SobreActivity.this.getLayoutInflater().inflate(R.layout.dialog_sobre, null);

                final AlertDialog dialog = new AlertDialog.Builder(SobreActivity.this)
                        .setCancelable(true)
                        .setView(view)
                        .create();

                dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                dialog.show();
            }
        };
    }

    private void init() {
        sobre = findViewById(R.id.layout_sobre_carb);
        dev = findViewById(R.id.layout_sobre_dev);
    }
}
