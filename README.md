<p align="center">
  <img src="https://github.com/lTheos/Images/blob/master/jpg/sized/SIERRA_PNG.jpg?raw=true" width="128">
  <h1 align="center">Sierra</h1>
</p>

Next generation Minecraft Terrain Engine for 1.16.x

<p align="center">
    <img src="https://github.com/lTheos/Images/blob/master/jpg/sized/clock-128.jpg?raw=true" width="128">
    <h1 align="center">Performance</h1>
</p>

Note that Sierra isn't your average world generator. While it does aim to create beautiful and realistic 
looking terrain in Minecraft, it isn't a performance beast. Many of the algorithms used within Sierra (such as 
the Jump Flooding Algorithm for hydraulic erosion) were originally tailored to be fast on a GPU. Since Minecraft
servers do not rely on a GPU, these algorithms cannot take advantage hereof and thus world creation times
may be (extremely, depending on the generator settings) significant!

<p align="center">
    <img src="https://github.com/lTheos/Images/blob/master/jpg/sized/find-128.jpg?raw=true" width="128">
    <h1 align="center">Sources</h1>
</p>

All sources are linked here. Thank you for every paper, article, author for helping
me creating this resource.

<ul>
    <li>Value Noise Derivatives: https://www.iquilezles.org/www/articles/morenoise/morenoise.htm</li>
    <li>Gradient Noise Derivatives: https://www.iquilezles.org/www/articles/gradientnoise/gradientnoise.htm</li>
    <li>Modeling Real-World Terrain with Exponentially Distributed Noise: http://jcgt.org/published/0004/02/01/</li>
    <li>Amortized Noise: http://jcgt.org/published/0003/02/02/</li>
    <li>Iñigo Quilez: https://www.iquilezles.org/www/index.htm</li>
    <li>Amit P: https://www.redblobgames.com/articles/noise/introduction.html</li>
    <li>Gilliam De Carpentier: https://www.decarpentier.nl/</li>
    <li>Perlin Noise: https://www.khanacademy.org/computing/computer-programming/programming-natural-simulations/programming-noise/a/perlin-noise</li>
    <li>Biome Interpolation: https://www.reddit.com/r/proceduralgeneration/comments/gqffg1/recently_got_proper_biome_interpolation_working/frspciw/?utm_source=share&utm_medium=web2x</li>
    <li>Domain Warping: https://www.iquilezles.org/www/articles/warp/warp.htm</li>
    <li>Fractal Brownian Motion: https://www.iquilezles.org/www/articles/fbm/fbm.htm</li>
    <li>Raymarching Terrains: https://www.iquilezles.org/www/articles/terrainmarching/terrainmarching.htm</li>
    <li>Generating realistic Archipelagos: https://medium.com/@yvanscher/playing-with-perlin-noise-generating-realistic-archipelagos-b59f004d8401</li>
    <li>Hydrology: https://weigert.vsos.ethz.ch/2020/04/15/procedural-hydrology/</li>
    <li>Hydraulic Erosion: https://weigert.vsos.ethz.ch/2020/04/10/simple-particle-based-hydraulic-erosion/</li>
    <li>Fractal model of Mountains with Rivers: http://algorithmicbotany.org/papers/mountains.gi93.pdf</li>
    <li>Biome Manipulation using ProtocolLib: https://gist.github.com/aadnk/4734454#file-randombiomes-java-L228</li>
    <li>Wavelet Noise: https://graphics.pixar.com/library/WaveletNoise/paper.pdf</li>
    <li>Simplex Noise Demystified: http://weber.itn.liu.se/~stegu/simplexnoise/simplexnoise.pdf</li>
    <li>Cubic Noise: https://jobtalle.com/cubic_noise.html</li>
    <li>Jump Flooding Algorithm: https://blog.demofox.org/2016/02/29/fast-voronoi-diagrams-and-distance-dield-textures-on-the-gpu-with-the-jump-flooding-algorithm/</li>
    <li>Amit P: http://www-cs-students.stanford.edu/~amitp/simblob/economy.html#geography</li>
    <li>Joining of Bezier Curves: https://www.math.ucla.edu/~baker/149.1.02w/handouts/dd_splines.pdf</li>
</ul>

<p align="center">Made with :heart: in <strong>Belgium</strong></p>
<p align="center">Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon"> www.flaticon.com</a></p>