package br.edu.ifsuldeminas.muz.alertaferrugem.utils;

import java.util.List;

import br.edu.ifsuldeminas.muz.alertaferrugem.model.Feedback;
import br.edu.ifsuldeminas.muz.alertaferrugem.dao.FeedbackDAO;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.Lavoura;
import br.edu.ifsuldeminas.muz.alertaferrugem.model.MetereologiaDiaria;
import br.edu.ifsuldeminas.muz.alertaferrugem.dao.MetereologiaDiariaDAO;

/**
 * Created by samuel on 12/07/2017.
 */

public class TrazFeedback
{

    public static Feedback Traz(Lavoura lav)
    {
        System.out.println(lav.getEst().getID());
        List<MetereologiaDiaria> medicoes = MetereologiaDiariaDAO.buscarTodos(lav.getEst().getID().toString());

        int flag = 0;
        int VSDs = 0;
        Double MolhamentoAnterior = 0.0;
        for(MetereologiaDiaria med : medicoes)
        {
            double TEMPERATURA = med.getTemperatura();
            double MOLHAMENTOFOLIAR= med.getUmidade();
            if(flag == 0)
            {
                MolhamentoAnterior = MOLHAMENTOFOLIAR;
                flag = 1;
                continue;
            }
            else
            {
                ///Parte lógica, extraida da tabela
                /// Não coloquei as comparações que dão 0 VSD's, pois são irrelevantes para o cálculo final

                if(TEMPERATURA >= 16 &&  TEMPERATURA <= 18 && MOLHAMENTOFOLIAR > 8 && MOLHAMENTOFOLIAR <= 17)
                    VSDs += 1;
                else if(TEMPERATURA >= 16 &&  TEMPERATURA <= 18 && MOLHAMENTOFOLIAR > 17 && MOLHAMENTOFOLIAR <= 24)
                    VSDs += 2;
                else if(TEMPERATURA >= 19 &&  TEMPERATURA <= 20 && MOLHAMENTOFOLIAR > 0 && MOLHAMENTOFOLIAR <= 8)
                    VSDs += 1;
                else if(TEMPERATURA >= 19 &&  TEMPERATURA <= 20 && MOLHAMENTOFOLIAR > 8 && MOLHAMENTOFOLIAR <= 17)
                    VSDs += 2;
                else if(TEMPERATURA >= 19 &&  TEMPERATURA <= 20 && MOLHAMENTOFOLIAR > 17 && MOLHAMENTOFOLIAR <= 24)
                    VSDs += 3;
                else if(TEMPERATURA >= 19 &&  TEMPERATURA <= 20 && MOLHAMENTOFOLIAR == 24 && MolhamentoAnterior == 24)
                    VSDs += 1;
                else if(TEMPERATURA >= 21 &&  TEMPERATURA <= 24 && MOLHAMENTOFOLIAR > 0 && MOLHAMENTOFOLIAR <= 8)
                    VSDs += 2;
                else if(TEMPERATURA >= 21 &&  TEMPERATURA <= 24 && MOLHAMENTOFOLIAR > 8 && MOLHAMENTOFOLIAR <= 17)
                    VSDs += 3;
                else if(TEMPERATURA >= 21 &&  TEMPERATURA <= 24 && MOLHAMENTOFOLIAR > 17 && MOLHAMENTOFOLIAR <= 24)
                    VSDs += 4;
                else if(TEMPERATURA >= 21 &&  TEMPERATURA <= 24 && MOLHAMENTOFOLIAR == 24 && MolhamentoAnterior == 24)
                    VSDs += 2;
                else if(TEMPERATURA >= 25 &&  TEMPERATURA <= 26 && MOLHAMENTOFOLIAR > 0 && MOLHAMENTOFOLIAR <= 8)
                    VSDs += 1;
                else if(TEMPERATURA >= 25 &&  TEMPERATURA <= 26 && MOLHAMENTOFOLIAR > 8 && MOLHAMENTOFOLIAR <= 17)
                    VSDs += 2;
                else if(TEMPERATURA >= 25 &&  TEMPERATURA <= 26 && MOLHAMENTOFOLIAR > 17 && MOLHAMENTOFOLIAR <= 24)
                    VSDs += 3;
                else if(TEMPERATURA >= 25 &&  TEMPERATURA <= 26 && MOLHAMENTOFOLIAR == 24 && MolhamentoAnterior == 24)
                    VSDs += 1;
                else if(TEMPERATURA >= 27 &&  TEMPERATURA <= 29 && MOLHAMENTOFOLIAR > 8 && MOLHAMENTOFOLIAR <= 17)
                    VSDs += 1;
                else if(TEMPERATURA >= 27 &&  TEMPERATURA <= 29 && MOLHAMENTOFOLIAR > 17 && MOLHAMENTOFOLIAR <= 24)
                    VSDs += 2;
            }
            MolhamentoAnterior = MOLHAMENTOFOLIAR;
        }

        FeedbackDAO dao = new FeedbackDAO();

        List<Feedback> feeds = dao.buscarTodos();
        System.out.println("VSDS = " + VSDs);
        if(lav.getAltaCarga())
        {
            if(VSDs >= 30)
                 return feeds.get(1);
            else
                 return feeds.get(0);
        }
        else
        {
            if(VSDs >= 50)
                return feeds.get(1);
            else
                return feeds.get(0);
        }
    }

}
