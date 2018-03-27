abstract class Figura {

	float X[];
	float Y[];

	Figura(float x[], float y[]) {
		this.X=x;
		this.Y=y;
	}

	public float calcularArea(Figura figura) {//solucao generica qualquer figura com lados>=3
		float dir=0;
		float esq=0;
		float S;

		float colunax[]= new float[this.X.length + 1];
		float colunay[]= new float[this.Y.length + 1];
		
		colunax = this.X;
		colunay = this.Y;
		
		colunax[this.X.length] = this.X[0];
		colunay[this.Y.length] = this.Y[0];

		for(int aux = 0; aux < this.X.length; aux++) {
			dir = (colunax[aux]*colunay[aux+1]) + dir;
			esq = (colunay[aux]*colunax[aux+1]) + esq;
		}

		S = esq-dir;
		S = S/2;

		if (S < 0){
			S = S*-1;
		}

		return S;
	}

	public void mover(float dx, float dy) {

		for(int aux = 0; aux < this.X.length; aux++) {
			this.X[aux] = this.X[aux] + dx;
		}

		for(int aux = 0; aux < this.Y.length; aux++) {
			this.Y[aux] = this.Y[aux] + dx;
		}
	}
}