// Area e perimetro di un triangolo dati i cateti.

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

/*
#include <iostream> // servirà per c++
using namespace std;
*/

// prototipi di funzioni

// NESSUNA variabile o costante fuori da main.

int main() {
  int a, b;

  printf("Dammi i due cateti del triangolo rettangolo: ");
  scanf("%d %d", &a, &b);

  printf("Il perimetro e' %.2f\n", a+b+sqrt(pow(a, 2)+pow(b, 2)));
  printf("L'area' e' %.2f\n", (a*b)/2.0);

  system("pause");
  return 0;
}
