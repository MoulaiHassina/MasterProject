import random
import numpy as np
# Get an example image
import matplotlib.pyplot as plt
import numpy as np
from matplotlib.patches import Circle
import matplotlib.patches as patches
import matplotlib.cbook as cbook
import random
from PIL import Image
from PIL import ImageDraw


nbrObsMin=0#10
nbrObsMax=0#20

min_detector=0 #5
max_detector=0 #10

from circle import draw_circle

def draw_rectangle(matrix,positionx,positiony ,sizex,sizey):
    maxcolumn = maxline = 0
    if sizex + positionx >= len(matrix):
        maxline = len(matrix)
    else:
        maxline = sizex + positionx
        
    if sizey + positiony >= len(matrix[0]):
        maxcolumn = len(matrix[0])
    else:
        maxcolumn = sizey + positiony
        
    for i in range(positionx, maxline):
        for j in range(positiony, maxcolumn):
            if matrix[i][j] < 1.0:
               matrix[i][j]=-1.0
               
    return  matrix



def show_matrix(matrix):
#    print("matrix:")
    s=""
    for i in range(len(matrix)):
        for j in range (len(matrix[0])):
            s=s+str(matrix[i][j])+" "
        s=s+"\n"

    return s





def matrix1(line,column):

    matrix = [[0.0 for i in range(0,column) ] for j in range(0,line)]
    return matrix



def draw_many_rectangle(path,range_dectector,target_detector,nb_target,sizes):
    s=""
    size = (sizes, sizes)


    print(size)
    #normalisation des max et min largeur et hauteur selon la taille de la frame
#    min_size_obstacle = int((3 * sizes)/100)
#    max_size_obstacle = int((6 * sizes)/100)
    
    im = Image.new('RGB', size, (255, 255, 255))
    draw = ImageDraw.Draw(im)
    im.save("hassina.png")
    image_file = cbook.get_sample_data('/home/izan/Desktop/Naila/USTHB/M2_SII/S2/Code/Simulator/hassina.png')

#    im = Image.new('RGB', size, (255, 255, 255))
#    draw = ImageDraw.Draw(im)
#    draw=cbook.get_sample_data(draw)

    img = plt.imread(image_file)
    nb = random.randint(nbrObsMin, nbrObsMax)
#    print(nb)
    
    column=img.shape[1]
    lines=img.shape[0]
    
    # Make some example data
    x = np.random.rand(nb+nb_target) * lines
    y = np.random.rand(nb+nb_target) * column
    
    matrix = matrix1(lines,column)
#    print("matrix","lines",lines,"column",column)
    
    s=s+"matrix:"+str(lines)+","+str(column)+"\n"
    s=s+str(target_detector)+"\n"
    x = [int(i) for i in x]
    y = [int(i) for i in y]

    # Create a figure. Equal aspect so circles look circular
    fig, ax = plt.subplots(1)

    ax.set_aspect('equal')
    #putting target in the first random position
    s=s+"cible:"+str(nb_target)+"\n"
    for i in range(0,nb_target):
      circ = Circle(( x[i],y[i] ), target_detector)
      matrix=draw_circle(matrix,y[i],x[i],target_detector)

#      print("cible:", y[i], x[i],target_detector)
      #checking if yt or xt or switching

      s=s+"start("+str(y[i])+","+str(x[i])+")\n"

      ax.add_patch(circ)
    x=[i for i in x if x.index(i)>=nb_target]
    y=[j for j in y if y.index(j)>=nb_target]
    # Show the image


#    s=s+"obstacle\n"
    
#TODO : comment this for none obsacles
#    for xx, yy in zip(x, y):
#        npsizey = random.randint(min_size_obstacle, max_size_obstacle)
#        nsizex = random.randint(min_size_obstacle, max_size_obstacle)
#        matrix = draw_rectangle(matrix, xx,yy, nsizex, npsizey)
#
#        matrix=draw_range_detector_3(matrix,xx,yy,nsizex,npsizey,range_dectector)
##        print("obstacle:","sizex",nsizex,"sizey",npsizey,"pointstart:",xx,yy,"portee",range_dectector)
#        s=s+"size("+str(nsizex)+","+str(npsizey)+"):pointstart("+str(xx)+","+str(yy)+"):portee:"+str(range_dectector)+"\n"

        
#        rect = patches.Rectangle((yy, xx), nsizex, npsizey, linewidth=1, edgecolor='black', facecolor='black')
#        ax.add_patch(rect)
        
        #  affect_range_values(xx,yy,0,nsizex,nsizex,img)
       # x1, y1, nsizex2, npsizey2 = range_of_rec(npsizey, nsizex, yy, xx, range_dectector)
       # rect = patches.Rectangle((x1, y1), npsizey2, nsizex2, linewidth=1, edgecolor='black', facecolor='none')
       # ax.add_patch(rect)

    ax.imshow(img)
    plt.draw()
    plt.savefig(path)
    plt.show()

    s=s+"matrix::\n"+show_matrix(matrix=matrix)
    return s

