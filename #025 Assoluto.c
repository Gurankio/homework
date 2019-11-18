/*
 * Jacopo Del Granchio
 * #025
 *
 * Calcola il valore assoluto di un numero in input.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");

  int n;

  printf("Dammi un numero: ");
  scanf("%d", &n);

  printf("Il valore assoluto � %d\n", abs(n));

  // getchar();
  // system("pause");
  return 0;
}
