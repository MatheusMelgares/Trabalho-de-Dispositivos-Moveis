package com.example.trabalhomark01;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class SlideAdapter extends PagerAdapter {

    //Objetos
    Context context;
    LayoutInflater layoutInflater;

    public SlideAdapter(Context context){
        this.context = context;
    }

    //Arrays de cada posicao das imagens e salva os valores nessas posições
    public int [] slide_images = {
            R.drawable.segunda,
            R.drawable.terca,
            R.drawable.quarta,
            R.drawable.quinta,
            R.drawable.sexta,
            R.drawable.sabado,
            R.drawable.domingo
    };

    //Arrays de cada posicao das Strings dos títulos e salva os valores nessas posições
    public String[] slide_headings = {
            "Segunda",
            "Terça",
            "Quarta",
            "Quinta",
            "Sexta",
            "Sábado",
            "Domingo"
    };

    //Arrays de cada posicao das descrições e salva os valores nessas posições
    public String[] slide_descs = {
            "Braço e Costas:\n\nBarra fixa ou graviton 5 séries 8-10 reps\nRosca alternada - 4 séries 8-10 reps\nRosca direta no cabo - 3 Séries 8-10 reps\nPuxada alta 3 Séries 8-10 reps\nRemada baixa no triângulo 4 Séries 8-10 reps\nRosca Scott: 3 Séries 10-reps",
            "Peito e Tríceps:\n\nSupino – 5 Séries 8-10 reps\nSupino inclinado halteres – 4 Séries 8-10 reps\nTríceps corda – 3 Séries 8-10 reps\nElevação lateral – 3 Séries 8-10 reps\nPeck deck fly – 3 Séries 8-10 reps",
            "Perna e Abdominal:\n\nAgachamento 5 Séries 8-10 reps\nLeg Press 4 Séries 8-10 reps\nExtensora 3 Séries 8-10 reps\nAbdominal crunch máquina 3 Séries 8-10 reps\nAbdominal oblíquo máquina 3 Séries 8-10 reps",
            "Repete Braço e Costas:\n\nBarra fixa ou graviton 5 séries 8-10 reps\nRosca alternada - 4 séries 8-10 reps\nRosca direta no cabo - 3 Séries 8-10 reps\nPuxada alta 3 Séries 8-10 reps\nRemada baixa no triângulo 4 Séries 8-10 reps\nRosca Scott: 3 Séries 10-reps",
            "Repete Peito e Tríceps:\n\nSupino – 5 Séries 8-10 reps\nSupino inclinado halteres – 4 Séries 8-10 reps\nTríceps corda – 3 Séries 8-10 reps\nElevação lateral – 3 Séries 8-10 reps\nPeck deck fly – 3 séries  8-10 reps",
            "Repete Perna e Abdominal:\n\nAgachamento 5 Séries 8-10 reps\nLeg Press 4 Séries 8-10 reps\nExtensora 3 Séries 8-10 reps\nAbdominal crunch máquina 3 Séries 8-10 reps\nAbdominal oblíquo máquina 3 Séries 8-10 reps",
            "Cardio caminhada de 30-45-60 minutos ou Descansar"
    };


    @Override
    //Conta o número
    public int getCount() {
        return slide_headings.length;
    }

    @Override

    public boolean isViewFromObject(View view, Object o) {
        return view == (RelativeLayout) o;
    }

    @Override
    //Método que adiciona o efeito dos slide em cada utilizando o LAYOUT INFLATER
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        //Inicia todos os IDs de imagem, descrição e icones
        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image );
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading );
        TextView slideDescription = (TextView) view.findViewById(R.id.slide_desc);

        //Passa os Arrays com a posição que esta instanciada, cada vez q um slide passar ele vai
        //Selecionar a imagem de acordo com a posição da imagem
        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);

        //Adiciona o container e a view desses slides
        container.addView(view);

        return view;
    }

    @Override
    //Método quando chegar a ultima pagina vai parar, e impedir que seja criada novos slides
    //impedindo erros
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //remove a view do objeto no RelativeLayout
        container.removeView((RelativeLayout) object);

    }
}
