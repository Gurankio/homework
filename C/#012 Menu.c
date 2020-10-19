/*
 * Jacopo Del Granchio
 * #012 15.10.2019
 *
 * Visualizza un semplice menu.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int main() {
  int option;

  printf("Menu di prova\n");
  printf("1) Immettere i dati\n");
  printf("2) Visualizzare i dati\n");
  printf("3) Modificare i dati\n");

  scanf("%d", &option);
  //printf("%c[1A\n", 033);
  switch (option) {
    case 1:
      printf("In esecuzione l'opzione 1\n");
      break;

    case 2:
      printf("In esecuzione l'opzione 1\n");
      break;

    case 3:
      printf("In esecuzione l'opzione 1\n");
      break;

    default:
      printf("Opzione inesistente\n");
  }

  getchar();
  // system("pause");
  return 0;
}
