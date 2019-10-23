/*
 * Jacopo Del Granchio
 * #000 GG.MM.YYYY
 *
 * Lorem ipsum dolor sit amet, consectetur adipisicing elit,
 * sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
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

  printf("Il secondo numero è %s\n", b > 0 ? "positivo." : "negativo.");
  printf("Il primo numero è %s\n", a % 2 == 0 ? "pari." : "dispari.");
  printf("La somma è %d\n", a + b);
  printf("La somma massima si ottine con %cA + %cB\n", a > 0 ? '+' : '-', b > 0 ? '+' : '-');

  // getchar();
  // system("pause");
  return 0;
}
