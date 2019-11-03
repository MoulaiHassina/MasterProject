package GA;

import Environnement.Dataset;

import java.util.ArrayList;

/**
 * Created by masterubunto on 20/02/19.
 */
public class GA
{

    int maxIter;
    int populationsize;
    double fitness;
    double perfectsolution;
    int mutation;
    Population p;
    Population pbest;
    Solution best;
    int mode;



    public void remove_unsatisfied_solution(int nbcibles)
    {

        Population pprime = new Population ();

        for(Solution s:this.p.getIndividus ())
        {
            if(s.getFobjective ()==nbcibles)
                pprime.add ( s );
        }
        this.p=pprime;
    }


    /*** BSO ***/

    public ArrayList GetparametsMax(int Maxflip, int Maxchances, int MaxnbrBee, int MaxlocalSize)
    {
        ArrayList param = new ArrayList (  );
        //  param.add ( Maxiter);
        param.add ( Maxflip );
        param.add ( Maxchances );
        param.add( MaxnbrBee);
        param.add(MaxlocalSize);

        return param;

    }

    public ArrayList GetparametsMin( int Minflip,int MinChances, int MinnbrBee, int MinlocalSize)
    {

        ArrayList param = new ArrayList (  );
        // param.add ( MinIter);
        param.add ( Minflip );
        param.add ( MinChances );
        param.add(MinnbrBee);
        param.add(MinlocalSize);

        return param;
    }

    /*** MBSO ***/

    public ArrayList GetparametsMaxMBSO(int Maxflip,int MaxChances, int MaxnbrBee, int MaxnbrSwarm)
    {

        ArrayList param = new ArrayList (  );
        param.add ( Maxflip);
        param.add ( MaxChances );
        param.add(MaxnbrBee);
        param.add(MaxnbrSwarm);

        return param;
    }
    public ArrayList GetparametsMinMBSO(int Minflip,int MinChances, int MinnbrBee, int MinnbrSwarm)
    {

        ArrayList param = new ArrayList (  );
        param.add ( Minflip);
        param.add ( MinChances );
        param.add(MinnbrBee);
        param.add(MinnbrSwarm);

        return param;
    }

    /***EHO***/

    public ArrayList GetparametersMineho(int nbclan,int elephants,int alpha , int beta)
    {

        ArrayList param = new ArrayList (  );
        param.add ( nbclan);
        param.add ( elephants );
        //  param.add ( maxgen );
        param.add(alpha);
        param.add(beta);

        return param;
    }
    public ArrayList GetparametersMaxeho(int nbclan,int elephants, int alpha , int beta)
    {

        ArrayList param = new ArrayList (  );
        param.add ( nbclan);
        param.add ( elephants );

        param.add(alpha);
        param.add(beta);

        return param;
    }

    /*** ESWSA ***/

    public ArrayList GetparametersMinEswsa(int elephants,int wt , int p)
    {
        ArrayList param = new ArrayList (  );

        param.add ( elephants );
        param.add(wt);
        param.add(p);

        return param;
    }
    public ArrayList GetparametersMaxEswsa(int elephants, int wt , int p)
    {
        ArrayList param = new ArrayList (  );

        param.add ( elephants );
        param.add(wt);
        param.add(p);

        return param;
    }
    /*** subswarmESWSA ***/

    public ArrayList GetparametersMinsubswarmEswsa(int nbclan,int elephants,int wt , int p)
    {
        ArrayList param = new ArrayList (  );
        param.add ( nbclan );
        param.add ( elephants );
        param.add(wt);
        param.add(p);


        return param;
    }
    public ArrayList GetparametersMaxsubswarmEswsa(int nbclan,int elephants, int wt , int p)
    {
        ArrayList param = new ArrayList (  );
        param.add ( nbclan );
        param.add ( elephants );
        param.add(wt);
        param.add(p);


        return param;
    }


    public void initBSOparametres()
    {
        Operation.setMiniLimits ( GetparametsMin ( 10,1,2,10 ) );
        Operation.setLimits ( GetparametsMax ( 20,3, 15, 10 ) );

    }

    public void initMultiBSOparametres()
    {
        Operation.setMiniLimits ( GetparametsMinMBSO ( 1,1,3,3 ) );
        Operation.setLimits ( GetparametsMaxMBSO ( 10,3, 5, 5 ) );

    }

    public void initEHOparametres()
    {
        Operation.setMiniLimits ( GetparametersMineho ( 2,2,1,1 ) );
        Operation.setLimits ( GetparametersMaxeho ( 5,5, 10, 10 ) );
    }

    public void initESWSAparametres()
    {
        Operation.setMiniLimits ( GetparametersMinEswsa ( 2,1,1) );
        Operation.setLimits ( GetparametersMaxEswsa ( 15,10, 10 ) );
    }
    public void initsubswarmESWSAparametres()
    {
        Operation.setMiniLimits ( GetparametersMinsubswarmEswsa ( 1,2,1,1) );
        Operation.setLimits ( GetparametersMaxsubswarmEswsa  ( 5,5,10, 10 ) );
    }


    public Solution GA ( int nbrCibles, Dataset env,int mode)
    { /**TODO: test with a random mutation parameter**/


        if(mode==0) initBSOparametres ();
        if(mode==1) initEHOparametres ();
        if(mode==2) initESWSAparametres();
        if(mode==3) initMultiBSOparametres();
        if(mode==4) initsubswarmESWSAparametres ();

        /** init une population **/
        p.GenerateRandompopulation(populationsize,mode);

        int i=0;
        best=new Solution ( Operation.getLimits ().size () );

        p.Evaluate_Population (  nbrCibles,  env,mode);

        if(p.getIndividus().size ()>0)

            while(i < maxIter){
                /** evalutate the population**/

                Solution[] parent= p.BestParents (mode,nbrCibles);

                /** best solution is first one in the array**/
                int bestparentindex;
                if(parent[0].getIteration() < parent[1].getIteration())
                {
                    bestparentindex=0;
                }
                else
                {
                    bestparentindex=1;
                }

                if (parent[bestparentindex].getFobjective() > best.getFobjective())
                { /** cloonnerr**/
                    best.Copy ( parent[bestparentindex],0 ,parent[bestparentindex].size ());
                    best.setFobjective ( parent[bestparentindex].getFobjective() );
                    fitness=best.getFobjective ();
                    best.setiteration ( parent[bestparentindex].getiteration() );
                }
                else
                    if (parent[bestparentindex].getFobjective() == best.getFobjective ())
                        if(parent[bestparentindex].getiteration() < best.getiteration ())
                        {
                            best.Copy ( parent[bestparentindex],0 ,parent[bestparentindex].size ());
                            best.setFobjective ( parent[bestparentindex].getFobjective () );
                            best.setiteration ( parent[bestparentindex].getiteration () );
                            fitness=best.getFobjective ();
                        }


                if(parent[bestparentindex].getFobjective () == nbrCibles)
                    pbest.add ( parent[bestparentindex] );


                /** crossover**/
                Solution crossover=Operation.Crossover ( parent[0],parent[1] );
                Solution crossover2=Operation.Crossover ( parent[1],parent[0] );


                crossover.Evaluate ( nbrCibles,  env,mode);
                crossover2.Evaluate ( nbrCibles,  env,mode);

                /** mutation**/
                Solution mutation1=Operation.Mutation (   crossover ,mutation,env.size,mode );
                Solution mutation2=Operation.Mutation (   crossover2 ,mutation,env.size,mode );

                mutation1.Evaluate ( nbrCibles,  env,mode);
                mutation2.Evaluate ( nbrCibles,  env,mode);

                p.add ( crossover );
                p.add ( crossover2 );
                p.add ( mutation1 );
                p.add ( mutation2 );

                i++;

              System.out.println("GA : itération: "+i);

            }

        Solution[] s =this.p.BestParents ( mode ,nbrCibles);
        if(s[0].getFobjective() == nbrCibles)
            this.pbest.add( s[0] );
        if(s[1].getFobjective() == nbrCibles)
            this.pbest.add( s[1] );

        if(pbest.getSize()==0)
            this.pbest.add( s[0] );


        System.out.println("*****************GA***************");//
        Solution Moyenne = new Solution(mode,0);
        long iter=500;
        for(Solution sol : pbest.individus){
            for(int p=0; p<Moyenne.size() ; p++){
                Moyenne.set(p,Moyenne.get(p)+sol.get(p));
                iter=sol.getIteration ();
            }
        }


        for(int p=0; p<Moyenne.size() ; p++){
            Moyenne.set(p,Moyenne.get(p)/pbest.getSize());
        }
        Moyenne.add ( (int) iter+20 );

    //    pbest.display ( mode );
     //   System.out.println("pbest: "+pbest.getSize());
          System.out.println("moyenne: "+Moyenne.toString(mode));


        return Moyenne;
    }


    public GA (int maxIter,int populationsize,int modefitness,int mutation)
    {
        this.maxIter=maxIter;
        this.mode=modefitness;
        this.populationsize=populationsize;
        this.p=new Population ();
        this.pbest=new Population ();
        this.mutation=mutation;

    }

}
