import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.mllib.util.MLUtils


    // Load and parse the data file.
    val data = MLUtils.loadLibSVMFile(sc, "file:/root/data-step.txt")
    // Split the data into training and test sets (30% held out for testing)
    //val splits = data.randomSplit(Array(0.7, 0.3))
    //val (trainingData, testData) = (splits(0), splits(1))
    val trainingData = MLUtils.loadLibSVMFile(sc, "file:/root/data-train-step.txt")
    val testData = MLUtils.loadLibSVMFile(sc, "file:/root/data-predict-step.txt")

    // Train a RandomForest model.
    // Empty categoricalFeaturesInfo indicates all features are continuous.
    val numClasses = 2
    val categoricalFeaturesInfo = Map[Int, Int]()
    val numTrees = 10 // Use more in practice.
    val featureSubsetStrategy = "auto" // Let the algorithm choose.
    val impurity = "gini"
    val maxDepth = 4
    val maxBins = 32

    val model = RandomForest.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
      numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)

    // Evaluate model on test instances and compute test error
    val labelAndPreds = testData.map { point =>
      val prediction = model.predict(point.features)
      (point.label, prediction)
    }

    labelAndPreds.coalesce(1).saveAsTextFile("file:/root/myPredict2")

    val testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / testData.count()
    //println("Test Error = " + testErr)
    //println("Learned classification forest model:\n" + model.toDebugString)

    // Save and load model
    //model.save(sc, "target/tmp/myRandomForestClassificationModel")
    //val sameModel = RandomForestModel.load(sc, "target/tmp/myRandomForestClassificationModel")
