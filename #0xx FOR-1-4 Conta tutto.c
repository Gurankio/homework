/*
 * Jacopo Del Granchio
 * #0xx 06.11.2019
 *
 * Conta i numeri positivi, nulli, negativi, pari e dispari.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");

  int n, t;
  int pos = 0, nul = 0, neg = 0, dis = 0, par = 0;

  printf("Quanti numeri interi? ");
  scanf("%d", &n);

  for (int i = 0; i < n; ++i) {
    printf("Dammi un numero: ");
    scanf("%d", &t);

    if (t > 0) pos++;

    if (t == 0) nul++;

    if (t < 0) neg++;

    if (t % 2 == 0) par++;
    else dis++;
  }

  printf("Positivi: %d\n", pos);
  printf("Nulli: %d\n", nul);
  printf("Negativi: %d\n", neg);
  printf("Pari: %d\n", par);
  printf("Dispari: %d\n", dis);


  // getchar();
  // system("pause");
  return 0;
}
