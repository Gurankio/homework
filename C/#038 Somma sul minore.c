/*
 * Jacopo Del Granchio
 * #038 12.11.2019
 *
 * Di due numeri in input somma il numero più piccolo alla somma di entrambi.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");

  int a, b;
  printf("Dammi un numero: ");
  scanf("%d", &a);

  do {
    printf("Dammi un numero diverso dal primo: ");
    scanf("%d", &b);
  } while (a == b);

  printf("Il risultato è %d\n", a + b + (a < b ? a : b));

  // getchar();
  // system("pause");
  return 0;
}
