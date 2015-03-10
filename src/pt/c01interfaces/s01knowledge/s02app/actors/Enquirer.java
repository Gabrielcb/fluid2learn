package pt.c01interfaces.s01knowledge.s02app.actors;

import pt.c01interfaces.s01knowledge.s01base.impl.BaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.impl.Declaracao;
import pt.c01interfaces.s01knowledge.s01base.inter.IBaseConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IDeclaracao;
import pt.c01interfaces.s01knowledge.s01base.inter.IEnquirer;
import pt.c01interfaces.s01knowledge.s01base.inter.IObjetoConhecimento;
import pt.c01interfaces.s01knowledge.s01base.inter.IResponder;

public class Enquirer implements IEnquirer
{
    IObjetoConhecimento obj;
    
	public Enquirer()
	{
        IBaseConhecimento objeto = new BaseConhecimento();
	}
	
	
	@Override
	public void connect(IResponder responder)
	{
		/* declaração das variaveis e dos objetos que serão utilizados no programa */
		IBaseConhecimento objeto = new BaseConhecimento();
		String animais[] = objeto.listaNomes(); 
        int num_animal = 0, cont = 0, i, j = 0;
        boolean acertei;
		obj = objeto.recuperaObjeto(animais[num_animal]);
		IDeclaracao declaracao = obj.primeira();
		
		/* laço que contar o número de perguntas realizadas */
		for(i = 0; i < animais.length; i++){
			obj = objeto.recuperaObjeto(animais[i]);
			declaracao = obj.primeira();
			while(declaracao != null){
				cont++;
				declaracao = obj.proxima();
			}
		}
		
	    IDeclaracao perguntas[];
	   
	    /* vetor de perguntas q guarda a pergunta e a resposta do responder */
	    perguntas = new IDeclaracao[cont];
	    String resposta, pergunta;
	 
		
	    
	    /*pegando os primeiros dados */
	    obj = objeto.recuperaObjeto(animais[num_animal]);
	    declaracao = obj.primeira();
	    
	    
	    /* laço que percorre todas as perguntas e ou todos os animais até encontrar a resposta */
		while(declaracao != null && num_animal < animais.length) { 
			
			cont = 0;
			/* laço que percorre o vetor para ver se a pergunta ja foi feita */
			while(perguntas[cont] != null){
				if(perguntas[cont].getPropriedade().equalsIgnoreCase(declaracao.getPropriedade())){
					break;
				}	
				else
					cont++;
			}
			
			/* recebe a resposta do responder e compara ela */
			if(perguntas[cont] == null){
				resposta = responder.ask(declaracao.getPropriedade());
				pergunta = declaracao.getPropriedade();
				/* compara a resposta obtida do responder com a pergunta feita */
				if(resposta.equalsIgnoreCase(declaracao.getValor())) {
					declaracao = new Declaracao(pergunta, resposta);
					perguntas[cont] = declaracao;
					declaracao = obj.proxima();
				}
				/* caso seja diferente, pula para o próximo animal */
				else { 
					num_animal++;
					declaracao = new Declaracao(pergunta, resposta);
					perguntas[cont] = declaracao;
					obj = objeto.recuperaObjeto(animais[num_animal]);
					declaracao = obj.primeira();
				}
			}
			else {
				/*se for igual a resposta do responder, vai para a proxima pergunta */
				if(perguntas[cont].getValor().equalsIgnoreCase(declaracao.getValor())){
					declaracao = obj.proxima();
				} 
				/* caso contrario, vai para o proximo animal */
				else {
					num_animal++;
					obj = objeto.recuperaObjeto(animais[num_animal]);
					declaracao = obj.primeira();
				}		
			}
		}
		
		/* se percorreu todas as perguntas, pega o animal e joga na booleana. */
		if(declaracao == null)
			acertei = responder.finalAnswer(animais[num_animal]);
		/* caso contrario, nao conhece o animal */
		else 
			acertei = responder.finalAnswer("nao conheco");
		
		/*caso tenha percorrido todas as perguntas entao, acertou o animal */
		if(acertei)
			System.out.println("Oba! Acertei!");
		/* caso contrario, errou*/
		else
			System.out.println("fuem! fuem! fuem!");
  
	
	} 
}
