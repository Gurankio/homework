/*
 * Jacopo Del Granchio
 * #033 06.11.2019
 *
 * Controlla se una sequenza di N numeri in input è crescente, decrescente.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <locale.h>

int main() {
  setlocale(LC_ALL, "");

  int n, l, t, maggiore = 1, minore = 1;

  printf("%s", "Quanti numeri? ");
  scanf("%d", &n);

  printf("%s", "Dammi un numero: ");
  scanf("%d", &t);

  for (int i = 1; i < n; i++) {
    l = t;
    printf("%s", "Dammi un numero: ");
    scanf("%d", &t);

    if (t > l) minore = 0;
    else maggiore = 0;
  }

  if (maggiore || minore) printf("%s%s.\n", "La sequenza è ", (maggiore ? "crescente" : "decrescente"));
  else printf("%s\n", "La sequenza non è nè crescente nè decrescente.");

  // getchar();
  // system("pause");
  return 0;
}
