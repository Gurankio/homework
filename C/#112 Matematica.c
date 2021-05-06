//
//  main.c
//  ForkJoin
//
//  Created by Gurankio on 19/04/2021.
//

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <sys/types.h>

#define wait_and_get(out) { int status; wait(&status); out = WEXITSTATUS(status); }
#define waitpid_and_get(out) { int status; waitpid(pid, &status, 0); out = WEXITSTATUS(status); }

int main(int argc, char const *argv[]) {
    
    // (7 * 2) + (25 : 5) * (8 - 5) 

    if (!fork()) {
        // child
        
        if (!fork()) {
            // child
            exit(8 - 5);
        }

        int b = (25 / 5);
        int c;
        wait_and_get(c);
        exit(b * c);
    }
    
    // father
    int a = 7 * 2;
    int d;
    wait_and_get(e);
    int e = a + d;
    printf("Risultato: %d\n", s[e]);
     
    return 0;
}

