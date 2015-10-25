package br.com.samirrolemberg.carb.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.samirrolemberg.carb.R;
import br.com.samirrolemberg.carb.model.Mock;

/**
 * Created by samir on 15/04/2015.
 */
public class MockAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Mock> itens;

    public MockAdapter(List<Mock> itens) {
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
        final HolderMock1 holder = (HolderMock1) paramVH;

        holder.id.setText(itens.get(position).getId()+"");
        holder.text.setText(itens.get(position).getText());
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
                inflate(R.layout.mocklayout, parent, false);
        return new HolderMock1(itemView);
    }

    public static class HolderMock1 extends RecyclerView.ViewHolder{

        protected TextView id;
        protected TextView text;

        public HolderMock1(View v) {
            super(v);
            id = (TextView) v.findViewById(R.id.mockid);
            text = (TextView) v.findViewById(R.id.mocktext);
        }

    }
}
