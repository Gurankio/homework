/*
 * Jacopo Del Granchio
 * #001
 *
 * Controlla se un numero è pari o dispari.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int a;

int main() {
  printf("Dammi un numero.\n");
  scanf(" %d", &a);
  printf("Il numero è %s.\n", a % 2 == 0 ? "pari" : "dispari");

  // equivalente a system("pause") ma multipiattaforma. system("pause") è solo per windows.
  // https://stackoverflow.com/questions/9386651/pause-screen-at-program-completion-in-c
  getchar();
  return 0;
}
