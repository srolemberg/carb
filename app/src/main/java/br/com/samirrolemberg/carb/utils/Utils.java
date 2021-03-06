package br.com.samirrolemberg.carb.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import br.com.samirrolemberg.carb.R;
import br.com.samirrolemberg.carb.model.Calibragem;

public class Utils {

    private Utils() {
    }

    public static CharSequence date_time_24_mask(Date date, Context context) {
        CharSequence data = date_mask(date, context);
        CharSequence time = time_24_mask(date, context);
        String conv = time.toString() + " " + data.toString();
        return conv;
    }

    public static CharSequence time_24_date_mask(Date date, Context context) {
        CharSequence data = date_mask(date, context);
        CharSequence time = time_24_mask(date, context);
        String conv = data.toString() + " " + time.toString();
        return conv;
    }

    public static CharSequence time_24_mask(Date date, Context context) {
        java.text.DateFormat d = DateFormat.getTimeFormat(context);
        return d.format(date);
    }

    public static CharSequence date_mask(Date date, Context context) {
        java.text.DateFormat d = DateFormat.getDateFormat(context);
        return d.format(date);
    }


    public static void atualizaMedia(List<Calibragem> calibragens, TextView tvaudio, TextView tvvideo, boolean isToolbar) {
        long sumAudio = 0;
        long sumVideo = 0;

        for (Calibragem calibragem : calibragens) {
            sumAudio += calibragem.getAudio() == null ? 0 : calibragem.getAudio();
            sumVideo += calibragem.getVideo() == null ? 0 : calibragem.getVideo();
        }

        if (sumAudio != 0) {
            double audio = sumAudio / calibragens.size();
            if (isToolbar) {
                tvaudio.setText(CustomContext.getContext().getString(R.string.audio_til__) + audio + CustomContext.getContext().getString(R.string._ms));
            } else {
                tvaudio.setText(audio + "");
            }
        } else {
            if (isToolbar) {
                tvaudio.setText(CustomContext.getContext().getString(R.string.audio_til__) + "0" + CustomContext.getContext().getString(R.string._ms));
            } else {
                tvaudio.setText("0");
            }
        }

        if (sumVideo != 0) {
            double video = sumVideo / calibragens.size();
            if (isToolbar) {
                tvvideo.setText(CustomContext.getContext().getString(R.string.video_til__) + video + CustomContext.getContext().getString(R.string._ms));
            } else {
                tvvideo.setText(video + "");
            }
        } else {
            if (isToolbar) {
                tvvideo.setText(CustomContext.getContext().getString(R.string.video_til__) + "0" + CustomContext.getContext().getString(R.string._ms));
            } else {
                tvvideo.setText("0");
            }
        }

    }

    public static int getNumeroColunas() {

        int u = 1;

        if (!isTablet()) {//se não é tablet
            if (CustomContext.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                u = 1;
            } else {//paisagem
                if (CustomContext.getContext().getResources().getDisplayMetrics().density < 1.5) {
                    u = 1;
                } else {
                    if (CustomContext.getContext().getResources().getDisplayMetrics().density > 3) {
                        u = 2;
                    } else {
                        u = 2;
                    }
                }
            }
        } else {
            if (CustomContext.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                if (CustomContext.getContext().getResources().getDisplayMetrics().density > 3) {//retrato
                    u = 3;
                } else {
                    u = 2;
                }
            } else {//paisagem
                if (CustomContext.getContext().getResources().getDisplayMetrics().density < 1.5) {
                    u = 2;
                } else {
                    if (CustomContext.getContext().getResources().getDisplayMetrics().density > 3) {
                        u = 4;
                    } else {
                        u = 3;
                    }
                }
            }

        }
        return u;
    }

    @SuppressWarnings("deprecation")
    public static Drawable getInstrumento(Integer indice) {
        //	<item>Instrumento...</item>
        //	<item>Guitarra</item>
        //	<item>Guitarra Pro</item>
        //	<item>Baixo</item>
        //	<item>Baixo Pro</item>
        //	<item>Bateria</item>
        //	<item>Bateria Avançado</item>
        //	<item>Bateria Pro</item>
        //	<item>Vocal</item>
        //	<item>Vocal Grupo</item>
        //	<item>Teclado</item>
        //	<item>Teclado Avançado</item>
        //	<item>Teclado Pro</item>
        //	<item>Controle</item>
        //	<item>Tapede Dança</item>
        //	<item>Teclado PC</item>

        if (indice == null) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.ic_guitar_grey600_48dp);
        }
        if (indice == 1) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_guitar);
        }
        if (indice == 2) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_guitar_real);
        }
        if (indice == 3) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_bass);
        }
        if (indice == 4) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_bass_real);
        }
        if (indice == 5) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_drums);
        }
        if (indice == 6) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_drums_advanced);
        }
        if (indice == 7) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_drums_real);
        }
        if (indice == 8) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_vocals);
        }
        if (indice == 9) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_vocals_harmony);
        }
        if (indice == 10) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_keys);
        }
        if (indice == 11) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_keys_advanced);
        }
        if (indice == 12) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_keys_real);
        }
        if (indice == 13) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_gamepad);
        }
        if (indice == 14) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_dance_pad);
        }
        if (indice == 15) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_keyboard);
        }
        return CustomContext.getContext().getResources().getDrawable(R.drawable.ic_guitar_grey600_48dp);
    }

    @SuppressWarnings("deprecation")
    public static Drawable getDispositivo(Integer indice) {
//		<item>Dispositivo...</item>
//		<item>TV CRT</item>1
//		<item>TV Wide</item>2
//		<item>Home Theater</item>3
//		<item>Caixa Acústica</item>4
//		<item>Fone de Ouvido</item>5
//		<item>Guitarra</item>6
//		<item>Guitarra Pro</item>7
//		<item>Baixo</item>8
//		<item>Baixo Pro</item>9
//		<item>Bateria</item>10
//		<item>Bateria Avançado</item>11
//		<item>Bateria Pro</item>12
//		<item>Vocal</item>13
//		<item>Vocal Grupo</item>14
//		<item>Teclado</item>15
//		<item>Teclado Avançado</item>16
//		<item>Teclado Pro</item>17
//		<item>Controle</item>18
//		<item>Tapede Dança</item>19
//		<item>Teclado PC</item>20
        if (indice == null) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.ic_guitar_grey600_48dp);
        }
        if (indice == 1) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.ic_guitar_grey600_48dp);
        }
        if (indice == 2) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.ic_guitar_grey600_48dp);
        }
        if (indice == 3) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.ic_guitar_grey600_48dp);
        }
        if (indice == 4) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.ic_guitar_grey600_48dp);
        }
        if (indice == 5) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.ic_guitar_grey600_48dp);
        }
        if (indice == 6) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_guitar);
        }
        if (indice == 7) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_guitar_real);
        }
        if (indice == 8) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_bass);
        }
        if (indice == 9) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_bass_real);
        }
        if (indice == 10) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_drums);
        }
        if (indice == 11) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_drums_advanced);
        }
        if (indice == 12) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_drums_real);
        }
        if (indice == 13) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_vocals);
        }
        if (indice == 14) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_vocals_harmony);
        }
        if (indice == 15) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_keys);
        }
        if (indice == 16) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_keys_advanced);
        }
        if (indice == 17) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_keys_real);
        }
        if (indice == 18) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_gamepad);
        }
        if (indice == 19) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_dance_pad);
        }
        if (indice == 20) {
            return CustomContext.getContext().getResources().getDrawable(R.drawable.instrument_keyboard);
        }
        return CustomContext.getContext().getResources().getDrawable(R.drawable.ic_guitar_grey600_48dp);
    }

    public static boolean isTablet() {
        return CustomContext.getContext().getResources().getBoolean(R.bool.isTablet);
    }
}
