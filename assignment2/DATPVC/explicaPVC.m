clear  % apaga tudo
load DPVC_116  % carreagr ficheiro  ->  DAT.ecg; DAT.ind; DAT.PVC

%----------------- DAT.ecg � o sinal
N=150000;

sinal=DAT.ecg;
figure(1)
plot( sinal(1:N)) 



%----------------- indice dos picos R
indpicos = find( DAT.ind < N);
picos = DAT.ind (indpicos);

plot(1:N, sinal(1:N), 'g', picos, sinal(picos), 'ro')

%----------------- quais sao normais e quais sao PVC 
pvc= DAT.pvc(indpicos);
indPVC= find(pvc==1);
indP = DAT.ind (indPVC);
plot(1:N, sinal(1:N), 'g', picos, sinal(picos), 'ro', ... 
      indP, sinal(indP),'b+')

%----------------------- larguea
for i=3:length(picos)
    picoR=picos(i);
    valorPico = sinal(picoR);
    esquerda=200;
    direita=200;
    x=sinal(picoR-esquerda:picoR+direita);
    largura=find(x>valorPico-300);
    length(largura)
    figure(1)
    plot(1:length(x),x,'g', largura, x(largura),'r')
    pause
end

