/*
 * Jacopo Del Granchio
 * #015  19.10.2019
<<<<<<< HEAD
=======
 *
>>>>>>> 9311976320145f8ba08dc75961e0a32628a1231d
 * Stampa da quanti giorni è composto un mese in input.
 */

#include <stdlib.h>
#include <stdio.h>
#include <math.h>

int main() {
  int m;

  printf("Dammi un numero: ");
  scanf("%d", &m);

  switch (m) {
    case 1:
    case 3:
    case 5:
    case 7:
    case 8:
    case 10:
    case 12:
      printf("31 giorni\n");
      break;

    case 4:
    case 6:
    case 9:
    case 11:
      printf("30 giorni\n");
      break;

    case 2:
      printf("28/29 giorni\n");
      break;

    default:
      printf("Non un mese valido.\n");
  }

  // getchar();
  // system("pause");
  return 0;
}
