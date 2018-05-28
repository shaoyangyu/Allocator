def defHole(index, size)
  {
    type: 'HOLE',
    index: index,
    size: size
  }
end

def defBall(index, size)
  {
    type: 'BALL',
    index: index,
    size: size
  }
end

def reducer
  lambda { |r, ball|
    _holes = r[:holes_rest]
    if (hole, index = _holes.each_with_index.find { |hole, _| hole[:size] >= ball[:size] }).nil?
      {
        balls_not_be_assigned: [*r[:balls_not_be_assigned], ball],
        holes_not_be_assigned: [*(r[:holes_not_be_assigned]), *_holes],
        holes_rest: [],
        assigned_pair: {}.merge(r[:assigned_pair])
      }
    else
      {
        balls_not_be_assigned: [*r[:balls_not_be_assigned]],
        holes_not_be_assigned: [*r[:holes_not_be_assigned], *(index > 0 ? _holes[0..index - 1] : [])],
        holes_rest: [*_holes[index + 1..-1]],
        assigned_pair: { hole => ball }.merge(r[:assigned_pair])
      }
    end
  }
end

def allocate(holes, balls)
  err = nil
  sorted_holes = holes.sort_by { |hole| hole[:size] }
  sorted_balls = balls.sort_by { |ball| ball[:size] }
  result = {}
  begin
     result = sorted_balls.reduce(balls_not_be_assigned: [],
                                  assigned_pair: {},
                                  holes_not_be_assigned: [],
                                  holes_rest: sorted_holes,
                                  &reducer)
   rescue => e
     err = e
   end
  [err, result[:balls_not_be_assigned], result[:holes_not_be_assigned], result[:assigned_pair]]
end
