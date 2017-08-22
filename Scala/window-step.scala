import org.apache.spark.ml.classification.{LogisticRegression, OneVsRest}
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator

// load data file.
val train = spark.read.format("libsvm").load("file:/root/data-train-step.txt")
val test = spark.read.format("libsvm").load("file:/root/data-predict.txt")

// instantiate the base classifier
val classifier = new LogisticRegression()

// instantiate the One Vs Rest Classifier.
val ovr = new OneVsRest().setClassifier(classifier)

// train the multiclass model.
val ovrModel = ovr.fit(train)

// score the model on test data.
val predictions = ovrModel.transform(test)

// obtain evaluator.
val evaluator = new MulticlassClassificationEvaluator().setMetricName("accuracy")

// compute the classification error on test data.
val accuracy = evaluator.evaluate(predictions)
println(s"Test Error : ${1 - accuracy}")