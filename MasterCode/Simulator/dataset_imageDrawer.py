from rangedetector import matrix1,max_size_obstacle,min_size_obstacle
from circle import draw_circle

from PIL import Image
from PIL import ImageDraw
import random as rnd
import numpy as np
import matplotlib.pyplot as plt
import random
N = 60
s = (500, 500)

im = Image.new('RGB', s, (255,255,255))
draw = ImageDraw.Draw(im)
im.save("hassina.png")
target_detector=5
range_detector=5
nb_target = 5
column = 500
lines = 500
nb = random.randint(10, 30)
nb_target = random.randint(2, 5)
# Make some example data
x = np.random.rand(nb) * lines
y = np.random.rand(nb) * column
matrix = matrix1(lines, column)
print("matrix", "lines", lines, "column", column)
#s = s + "matrix" + ":" + str(lines) + "," + str(column) + "\n"
x = [int(i) for i in x]
y = [int(i) for i in y]

ytarget = np.random.rand(nb_target) * lines
xtarget = np.random.rand(nb_target) * column
xtarget = [int(i) for i in xtarget]
ytarget = [int(i) for i in ytarget]
# Create a figure. Equal aspect so circles look circular
for xt, yt in zip(xtarget, ytarget):

    npsizey = random.randint(min_size_obstacle, max_size_obstacle)
    nsizex = random.randint(min_size_obstacle, max_size_obstacle)
    print(nsizex,npsizey)
    draw.rectangle(((xt, yt), (xt+nsizex, yt+npsizey)), fill="black")
    #color  = (int(rnd.random() * 256), int(rnd.random() * 256), int(rnd.random() * 256), int(alpha * 256))
plt.imshow(np.asarray(im),
           origin='lower')
plt.show()
