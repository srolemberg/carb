package br.com.samirrolemberg.carb.adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.samirrolemberg.carb.CalibragemActivity;
import br.com.samirrolemberg.carb.MainActivity;
import br.com.samirrolemberg.carb.R;
import br.com.samirrolemberg.carb.conn.DatabaseManager;
import br.com.samirrolemberg.carb.daos.DAOCalibragem;
import br.com.samirrolemberg.carb.daos.DAODispositivo;
import br.com.samirrolemberg.carb.model.Calibragem;
import br.com.samirrolemberg.carb.model.Dispositivo;
import br.com.samirrolemberg.carb.utils.C;
import br.com.samirrolemberg.carb.utils.U;

/**
 * Created by samir on 15/04/2015.
 */
public class DispositivoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Dispositivo> itens;
    private MainActivity activity;

    public DispositivoAdapter(List<Dispositivo> itens, MainActivity activity) {
        super();
        this.itens = itens;
        this.activity = activity;
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        if (position==0) {
//            return 0;
//        }else{
//            return 1;
//        }
//    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder paramVH, final int position) {
        //if (getItemViewType(position)!=0) {}else{}
        final HolderDispositivo holder = (HolderDispositivo) paramVH;

        holder.tvNome.setText(itens.get(position).getNome());

        //holder.tvDataCriacao.setText(U.time_24_date_mask(itens.get(position).getDataCriacao(), C.getContext()));

        if(itens.get(position).getUltimaAtualizacao() == null){
            holder.tvDataCriacao.setText(U.time_24_date_mask(itens.get(position).getDataCriacao(), holder.layCard.getContext()));
        }else{
            holder.tvLabelDataCriacao.setText(R.string.atualizado_em__);
            holder.tvDataCriacao.setText(U.time_24_date_mask(itens.get(position).getUltimaAtualizacao(), holder.layCard.getContext()));
        }


        U.atualizaMedia(
                (new DAOCalibragem(C.getContext())).listarTudo(itens.get(position)),
                holder.tvAudio,
                holder.tvVideo,
                false);

        holder.ivTipo.setImageDrawable(U.getDispositivo(itens.get(position).getTipo()));




        final PopupMenu popupMenu = new PopupMenu(holder.btnMenuDispositivo.getContext(), holder.btnMenuDispositivo);
        popupMenu.inflate(R.menu.menu_adapter_dispositivo);

        holder.btnMenuDispositivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
        holder.btnMenuDispositivo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                popupMenu.show();
                return false;
            }
        });
        holder.layCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.layCard.getContext(), CalibragemActivity.class);
                intent.putExtra("dispositivo", itens.get(position));
                holder.layCard.getContext().startActivity(intent);
            }
        });
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
                if (item.getItemId() == R.id.menu_dispositivo_detalhes) {
                    Intent intent = new Intent(holder.layCard.getContext(), CalibragemActivity.class);
                    intent.putExtra("dispositivo", itens.get(position));
                    holder.layCard.getContext().startActivity(intent);
                    return true;
                }
                if (item.getItemId() == R.id.menu_dispositivo_editar) {
                    activity.exibirDialog(activity, itens.get(position));
                    return true;
                }
                if (item.getItemId() == R.id.menu_dispositivo_excluir) {
                    AlertDialog alertDialog = new AlertDialog.Builder(holder.layCard.getContext())
                            .setTitle(activity.getString(R.string.excluir))
                            .setMessage(activity.getString(R.string.dialog_msg_excluir_item) + itens.get(position).getNome() + "' ?")
                            .setCancelable(true)
                            .setPositiveButton(activity.getString(R.string.sim), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //remove todas as calibragens de um dispositivo
                                    DAOCalibragem daoCalibragem = new DAOCalibragem(holder.layCard.getContext());
                                    List<Calibragem> calibragens = daoCalibragem.listarTudo(itens.get(position));

                                    for (Calibragem c : calibragens) {
                                        daoCalibragem.remover(c);
                                    }
                                    DatabaseManager.getInstance().closeDatabase();//fecha conexão de calibragem

                                    DAODispositivo daoDispositivo = new DAODispositivo(holder.layCard.getContext());
                                    daoDispositivo.remover(itens.get(position));
                                    DatabaseManager.getInstance().closeDatabase();

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
//        if (viewType==0) {
//            itemView = LayoutInflater.
//                    from(parent.getContext()).
//                    inflate(R.layout_toolbar_calibragem.menu_conteudo, parent, false);
//            return new HolderMenuConteudo(itemView);
//        }
        itemView = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.adapter_dispositivos, parent, false);
        return new HolderDispositivo(itemView);
    }

    public static class HolderDispositivo extends RecyclerView.ViewHolder{

        protected Button btnMenuDispositivo;
        protected TextView tvNome, tvAudio, tvVideo, tvDataCriacao, tvLabelDataCriacao;
        protected ImageView ivTipo;
        protected CardView layCard;

        public HolderDispositivo(View v) {
            super(v);
            btnMenuDispositivo = (Button) v.findViewById(R.id.btnMenuDispositivo);
            tvNome = (TextView) v.findViewById(R.id.tvNomeDispositivo);
            layCard = (CardView) v.findViewById(R.id.cardDispositivo);

            ivTipo = (ImageView) v.findViewById(R.id.ivTipoDispositivo);
            tvAudio = (TextView) v.findViewById(R.id.tvValorAudio);
            tvVideo = (TextView) v.findViewById(R.id.tvValorVideo);
            tvDataCriacao = (TextView) v.findViewById(R.id.tvDataCriacao);
            tvLabelDataCriacao = (TextView) v.findViewById(R.id.tvLabelDataCriacao);
        }

    }
}
