//
//  main.c
//  ForkJoin
//
//  Created by Gurankio on 19/04/2021.
//

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/mman.h>

#define malloc_shared(size) mmap(NULL, size, PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, -1, 0);

enum { a, b, c, d, e };

int main(int argc, char const *argv[]) {
    int* s = malloc_shared(sizeof(int) * 5);

    if (!fork()) {
        // child
        if (!fork()) {
            // child
            s[c] = 18 / 9;
            exit(0);
        } else {
            // father
            s[b] = 6 - 4;
            wait(NULL);
            s[d] = s[b] * s[c]; 
            exit(0);
        }
    } else {
        // father
        s[a] = 3 + 2;
        wait(NULL);
        s[e] = s[a] + s[d]; 
        printf("Risultato: %d\n", 9);
    }

    return 0;
}

