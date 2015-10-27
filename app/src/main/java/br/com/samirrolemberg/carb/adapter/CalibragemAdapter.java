package br.com.samirrolemberg.carb.adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.samirrolemberg.carb.R;
import br.com.samirrolemberg.carb.conn.DatabaseManager;
import br.com.samirrolemberg.carb.daos.DAOCalibragem;
import br.com.samirrolemberg.carb.daos.DAODispositivo;
import br.com.samirrolemberg.carb.model.Calibragem;
import br.com.samirrolemberg.carb.utils.C;
import br.com.samirrolemberg.carb.utils.U;

/**
 * Created by samir on 15/04/2015.
 */
public class CalibragemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Calibragem> itens;

    public CalibragemAdapter(List<Calibragem> itens) {
        super();
        this.itens=itens;
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

        holder.tvDataCriacao.setText(U.time_24_date_mask(itens.get(position).getDataCriacao(), holder.layCard.getContext()));
        holder.tvVideo.setText(itens.get(position).getVideo().toString());
        holder.tvAudio.setText(itens.get(position).getAudio().toString());
        holder.tvDescricao.setText(itens.get(position).getDescricao());
        holder.tvTitulo.setText(itens.get(position).getTitulo());
        holder.ivTipo.setImageDrawable(U.getInstrumento(itens.get(position).getTipo()));

        final PopupMenu popupMenu = new PopupMenu(holder.btnMenuCalibragem.getContext(), holder.btnMenuCalibragem);
        popupMenu.inflate(R.menu.menu_adapter_calibragem);

        holder.btnMenuCalibragem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
        holder.btnMenuCalibragem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                popupMenu.show();
                return false;
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
                if(item.getItemId() == R.id.menu_calibragem_editar){
                    Toast.makeText(holder.btnMenuCalibragem.getContext(),"Click Menu Editar", Toast.LENGTH_LONG).show();
                    return true;
                }
                if(item.getItemId() == R.id.menu_calibragem_excluir){
                    AlertDialog alertDialog = new AlertDialog.Builder(holder.layCard.getContext())
                            .setTitle("Excluir")
                            .setMessage("Deseja realmente excluir o item: '" + itens.get(position).getTitulo() + "' ?")
                            .setCancelable(true)
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
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
                            .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();                    return true;
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
                inflate(R.layout.adapter_calibragens, parent, false);
        return new HolderDispositivo(itemView);
    }

    public static class HolderDispositivo extends RecyclerView.ViewHolder{

        protected TextView tvTitulo, tvDescricao, tvAudio, tvVideo, tvDataCriacao;
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
            btnMenuCalibragem = (Button) v.findViewById(R.id.btnMenuCalibragem);
        }

    }
}
