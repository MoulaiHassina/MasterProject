
import matplotlib.pyplot as plt

def draw_circle (matrix,px,py,rayon):
    maxline=abs(px)+rayon
    maxcolonne=abs(py)+rayon
    if abs(px)+rayon >= len(matrix):
        maxline=len(matrix)
    if abs(py)+rayon >= len(matrix[0]):
        maxcolonne=len(matrix[0])

    for i in range(0,rayon+1):
        for x in range((px-rayon),maxline):
            for y in range((py - rayon), maxcolonne):
             if (x-px)**2 + (y-py)**2 < (rayon-i)**2:
                 if x >-1 and y >-1 :
                     if(matrix[x][y] < float((i+1)/rayon)):
                         matrix[x][y] = float((i+1)/rayon)

    return  matrix





#(1. - (double) (Math.abs(k) + Math.abs(j)) / (w * PORTEE + v)) * 100)) / 100