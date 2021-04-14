package pandemie;

/**
 * Launches the simulation with names of simulation views to animate.
 * @param args Unused.
 * @author Peter Sander
 */
class Main {

    public static void main(String[] args) {
        // pass along which simulation views to instantiate
        Animator.main(
                "pandemie.GridView"
        );
    }
}