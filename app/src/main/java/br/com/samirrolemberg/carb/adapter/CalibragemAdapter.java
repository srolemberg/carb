package br.com.samirrolemberg.carb.adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.samirrolemberg.carb.activity.CalibragemActivity;
import br.com.samirrolemberg.carb.R;
import br.com.samirrolemberg.carb.conn.DatabaseManager;
import br.com.samirrolemberg.carb.model.Calibragem;
import br.com.samirrolemberg.carb.utils.Utils;

/**
 * Created by samir on 15/04/2015.
 */
public class CalibragemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Calibragem> itens;

    private CalibragemActivity activity;

    public CalibragemAdapter(List<Calibragem> itens, CalibragemActivity activity) {
        super();
        this.itens = itens;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder paramVH, final int position) {
        final HolderDispositivo holder = (HolderDispositivo) paramVH;

        if(itens.get(position).getUltimaAtualizacao() == null){
            holder.tvDataCriacao.setText(Utils.time_24_date_mask(itens.get(position).getDataCriacao(), holder.layCard.getContext()));
        }else{
            holder.tvLabelDataCriacao.setText(R.string.atualizado_em__);
            holder.tvDataCriacao.setText(Utils.time_24_date_mask(itens.get(position).getUltimaAtualizacao(), holder.layCard.getContext()));
        }

        holder.tvVideo.setText(itens.get(position).getVideo().toString());
        holder.tvAudio.setText(itens.get(position).getAudio().toString());
        holder.tvDescricao.setText(itens.get(position).getDescricao());
        if(TextUtils.isEmpty(itens.get(position).getDescricao())){
            holder.tvDescricao.setVisibility(View.GONE);
        }
        holder.tvTitulo.setText(itens.get(position).getTitulo());
        holder.ivTipo.setImageDrawable(Utils.getInstrumento(itens.get(position).getTipo()));

        final PopupMenu popupMenu = new PopupMenu(holder.btnMenuCalibragem.getContext(), holder.btnMenuCalibragem);
        popupMenu.inflate(R.menu.menu_adapter_calibragem);

        //click no botão do menu
        holder.btnMenuCalibragem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
        //long click no botão do menu
        holder.btnMenuCalibragem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                popupMenu.show();
                return false;
            }
        });
        //click no cartão
        holder.layCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.exibirDialog(activity, itens.get(position));
            }
        });
        //long click no cartão
        holder.layCard.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                popupMenu.show();
                return false;
            }
        });

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.menu_calibragem_editar){
                    activity.exibirDialog(activity, itens.get(position));
                    return true;
                }
                if(item.getItemId() == R.id.menu_calibragem_excluir){
                    AlertDialog alertDialog = new AlertDialog.Builder(holder.layCard.getContext())
                            .setTitle(activity.getString(R.string.excluir))
                            .setMessage(activity.getString(R.string.dialog_msg_excluir_item) + itens.get(position).getTitulo() + "' ?")
                            .setCancelable(true)
                            .setPositiveButton(activity.getString(R.string.sim), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //remove todas as calibragens de um dispositivo
                                    DAOCalibragem daoCalibragem = new DAOCalibragem(holder.layCard.getContext());
                                    daoCalibragem.remover(itens.get(position));
                                    DatabaseManager.getInstance().closeDatabase();//fecha conexão de calibragem

                                    itens.remove(position);
                                    notifyItemRemoved(position);

                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton(activity.getString(R.string.nao), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.adapter_calibragens, parent, false);
        return new HolderDispositivo(itemView);
    }

    public static class HolderDispositivo extends RecyclerView.ViewHolder{

        protected TextView tvTitulo, tvDescricao, tvAudio, tvVideo, tvDataCriacao, tvLabelDataCriacao;
        protected ImageView ivTipo;
        protected CardView layCard;
        protected Button btnMenuCalibragem;

        public HolderDispositivo(View v) {
            super(v);
            layCard = (CardView) v.findViewById(R.id.cardCalibragem);
            ivTipo = (ImageView) v.findViewById(R.id.ivTipoCalibragem);
            tvTitulo = (TextView) v.findViewById(R.id.tvTituloCalibragem);
            tvDescricao = (TextView) v.findViewById(R.id.tvDescricaoCalibragem);
            tvAudio = (TextView) v.findViewById(R.id.tvValorAudio);
            tvVideo = (TextView) v.findViewById(R.id.tvValorVideo);
            tvDataCriacao = (TextView) v.findViewById(R.id.tvDataCriacao);
            tvLabelDataCriacao = (TextView) v.findViewById(R.id.tvLabelDataCriacao);
            btnMenuCalibragem = (Button) v.findViewById(R.id.btnMenuCalibragem);
        }

    }
}
