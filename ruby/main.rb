#!/usr/bin/env ruby
require 'pry'
require 'ap'
require './hole_and_ball'

if ARGF.argv.empty?
  # create random data if no input
  holes = (1..100).to_a.sample(4).each_with_index.map { |size, index| defHole(index + 1, size) }
  balls = (1..100).to_a.sample(2).each_with_index.map { |size, index| defBall(index + 1, size) }
else
  # init test data from input
  input = ARGF.readlines.to_a
  holes = input[0].chop.split(',').each_with_index.map { |size, index| defHole(index + 1, size.to_i) }
  balls = input[1].chop.split(',').each_with_index.map { |size, index| defBall(index + 1, size.to_i) }
end

# allocate the holes and balls
err, balls_not_be_assigned, holes_not_be_assigned, assigned_pair = allocate(holes, balls)

# output the allocated results
if err.nil?
  puts('[Input]Balls Are:')
  ap(balls)
  puts('---------------------')
  puts('[Input]Holes Are:')
  ap(holes)
  puts('---------------------')
  puts('[Output]Not-assigned-Balls:')
  ap(balls_not_be_assigned)
  puts('---------------------')
  puts('[Output]Not-assigned-Holes:')
  ap(holes_not_be_assigned)
  puts('---------------------')
  puts('[Output]Assigned pair:')
  ap(assigned_pair)
else
  puts "there is error:#{err}"
end

puts 'Done'
