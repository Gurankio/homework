/*
 * Jacopo Del Granchio
 * #026
 *
 * Calcola varie operazioni su due numeri in input.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");

  int a, b;

  printf("Dammi due numeri: ");
  scanf("%d %d\n", &a, &b);

  printf("Il secondo numero � %s\n", b > 0 ? "positivo." : "negativo.");
  printf("Il primo numero � %s\n", a % 2 == 0 ? "pari." : "dispari.");
  printf("La somma � %d\n", a + b);
  printf("La somma massima si ottine con %cA + %cB\n", a > 0 ? '+' : '-', b > 0 ? '+' : '-');

  // getchar();
  // system("pause");
  return 0;
}
